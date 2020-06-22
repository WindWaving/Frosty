const mainRouter = require('koa-router')()
const compose=require('koa-compose')
const { resolve } = require('path')
const glob=require('glob')
mainRouter.prefix('/frosty/api')

apiRouter = () => {
  let routers = [];
  glob.sync(resolve(__dirname, './', '**/*.js'))
    .filter(value => (value.indexOf('index.js') === -1))
    .map(router => {
      require(router).prefix('/frosty/api')
      routers.push(require(router).routes())
      routers.push(require(router).allowedMethods())
    })
  return compose(routers)
}


module.exports = {
  mainRouter,
  apiRouter
}
