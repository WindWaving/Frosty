const {sequelize,Sequelize}=require("./db")

const Favorites=sequelize.define('favorites',{
    fid:{
        type:Sequelize.INTEGER,
        autoIncrement:true,
        primaryKey:true
    },
    uid:Sequelize.INTEGER,
    itemId:Sequelize.INTEGER,
    type:Sequelize.STRING
})

module.exports=Favorites