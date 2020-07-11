const {sequelize,Sequelize}=require("./db")

const Reply=sequelize.define('reply',{
    rid:{
        type:Sequelize.INTEGER,
        autoIncrement:true,
        primaryKey:true
    },
    uid:Sequelize.INTEGER,
    //parentId:Sequelize.INTEGER,
    content:Sequelize.STRING,
    images:{
        type:Sequelize.STRING,
        allowNull:true
    }
})

module.exports=Reply