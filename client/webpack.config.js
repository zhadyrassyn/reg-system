//plugins
const HtmlPlugin = require('html-webpack-plugin')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const path = require("path")

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
    filename: 'app.bundle.js'
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
    extractTextPlugin
  ],
  devServer: {
    historyApiFallback: true,
    contentBase: './'
  }
};
