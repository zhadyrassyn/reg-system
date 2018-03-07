//plugins
const HtmlPlugin = require('html-webpack-plugin')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const path = require("path")
const webpack = require('webpack')

const htmlPlugin = new HtmlPlugin({
  title: 'Online registration system',
  template: './index.html',
  filename: 'index.html',
  hash: true
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
    filename: 'app.bundle.js',
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
    htmlPlugin,
    extractTextPlugin,
    new webpack.DefinePlugin({
      'process.env.NODE_ENV': JSON.stringify(process.env.NODE_ENV)
    })
  ],
  devServer: {
    historyApiFallback: true,
    contentBase: './'
  }
};
