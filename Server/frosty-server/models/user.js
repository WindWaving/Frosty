const {sequelize,Sequelize}=require("./db")

const User=sequelize.define('user',{
    uid:{
        type:Sequelize.INTEGER,
        autoIncrement:true,
        primaryKey:true
    },
    nickname:{
        type:Sequelize.STRING,
        unique:true
    },
    password:Sequelize.STRING,
    avatarUrl:Sequelize.STRING,
    phoneNumber:Sequelize.STRING
})

module.exports=User