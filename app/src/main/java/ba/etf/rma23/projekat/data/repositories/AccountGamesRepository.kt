package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game

class AccountGamesRepository {
    private lateinit var accHash: String
    private var accAge: Int = 0

    public fun setHash(acHash:String):Boolean{
        accHash = acHash
        return true;
    }
    public fun getHash():String{
        return accHash;
    }
    public fun setAge(age:Int):Boolean{
        accAge=age
        if(accAge<3 || accAge>100)
            return false
        return true
    }
    public fun saveGame(game:Game) {

    }

}