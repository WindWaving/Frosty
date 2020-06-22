const Sequelize=require("sequelize")

const sequelize=new Sequelize('frosty','root','usbw',{
    host:'localhost',
    dialect:'mysql',
    port:'3307',
    timezone:'+08:00'
})

module.exports={
    Sequelize,
    sequelize
}