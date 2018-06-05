let config = null
console.log('env ', process.env.NODE_ENV)

switch (process.env.NODE_ENV) {
  case 'production':
    config = require('./prod')
    break
  case 'pre':
    config = require('./pre')
    break
  default:
    config = require('./dev')
    break
}

export default config.default