//plugins
const HtmlPlugin = require('html-webpack-plugin')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const CleanWebpackPlugin = require('clean-webpack-plugin')
const path = require("path")
const webpack = require('webpack')

const cleanPlugin = new CleanWebpackPlugin(
  ['./build/client'], {
    root: __dirname,
    verbose: true,
    dry: false
  })


const htmlPlugin = new HtmlPlugin({
  title: 'Online registration system',
  template: './index.html',
  filename: 'index.html',
  hash: true,
  excludeChunks: ['base'],
  minify: {
    collapseWhitespace: true,
    collapseInlineTagWhitespace: true,
    removeComments: true,
    removeRedundantAttributes: true
  }
})

const extractTextPlugin = new ExtractTextPlugin({
  filename: "bundle.css",
  disable: false,
  allChunks: true
})

module.exports = {
  entry: [
    './src/index.js'
  ],
  resolve: {
    extensions: ['.js', '.jsx']
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].[chunkhash].js',
    publicPath: "/"
  },
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            presets:[
              "es2015", "react", "stage-0"
            ],
            cacheDirectory: true,
            plugins: [
              ["transform-runtime", {
                "helpers": false,
                "polyfill": false,
                "regenerator": true,
                "moduleName": "babel-runtime"
              }]
            ]
          },
        }
      },
      {
        test: /\.less$/,
        use: extractTextPlugin.extract({
          fallback: "style-loader",
          use: ["css-loader", "less-loader"],
          publicPath: "/dist"
        })
      }
    ]
  },
  plugins: [
    cleanPlugin,
    htmlPlugin,
    extractTextPlugin,
    new webpack.DefinePlugin({
      'process.env.NODE_ENV': JSON.stringify(process.env.NODE_ENV)
    }),
    // new webpack.optimize.CommonsChunkPlugin({
    //   name: 'client',
    //   filename: 'client.[chunkhash].js',
    //   minChunks (module) {
    //     return module.context &&
    //       module.context.indexOf('node_modules') >= 0;
    //   }
    // }),
    // new webpack.optimize.UglifyJsPlugin({
    //   compress: {
    //     warnings: false,
    //     screw_ie8: true,
    //     conditionals: true,
    //     unused: true,
    //     comparisons: true,
    //     sequences: true,
    //     dead_code: true,
    //     evaluate: true,
    //     if_return: true,
    //     join_vars: true
    //   },
    //   output: {
    //     comments: false
    //   }
    // }),
    // new webpack.HashedModuleIdsPlugin()
  ],
  devServer: {
    historyApiFallback: true,
    contentBase: './'
  }
};
