const {sequelize,Sequelize}=require("./db")

const Comment=sequelize.define('comment',{
    cid:{
        type:Sequelize.INTEGER,
        autoIncrement:true,
        primaryKey:true
    },
    type:Sequelize.STRING,
    parentId:Sequelize.INTEGER,
    uid:Sequelize.INTEGER,
    content:Sequelize.STRING
})

module.exports=Comment