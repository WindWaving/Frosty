const {sequelize,Sequelize}=require("./db")

const Music=sequelize.define('music',{
    mid:{
        type:Sequelize.INTEGER,
        autoIncrement:true,
        primaryKey:true
    },
    musicUrl:{
        type:Sequelize.STRING
    },
    name:Sequelize.STRING,
    words:Sequelize.TEXT,
    images:{
        type:Sequelize.STRING,
        allowNull:true
    },
    //authorId:Sequelize.INTEGER,
    likes:{
        type:Sequelize.INTEGER,
        defaultValue:0
    }
})

module.exports=Music