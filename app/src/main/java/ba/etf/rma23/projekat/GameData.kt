package ba.etf.rma23.projekat

class GameData {
    companion object {
        fun getAll2(): List<Game> {
            var usr1 = listOf(
                UserReview(
                    "sword_robber2",
                    1679959758,
                    "This game is a lot better than pubg not too much violence"
                ),
                UserReview(
                    "Acalodo",
                    1678710558,
                    "This Game is Not that violent and non relistic. The game is fun and should be played by every one."
                ),
                UserRating("J G", 1615638558, 2.5),
                UserRating("Lowkstaa", 1615984158, 4.3),
                UserReview(
                    "crispycankles",
                    1647520158,
                    "I don’t necessarily hate the game just the people who made it, it use to be something great but now I can’t even play 5 minutes of it, it’s not fun anymore, they took all the fun aspects from it by adding crappier weapons in it"
                )
            )
            var usr2 = listOf(
                UserRating("OhRly", 1345552158, 4.0),
                UserReview(
                    "Termin8ter",
                    1499689758,
                    "Counter- Strike: Global Offensive is probably the most popular multiplayer game on PC at the moment, along with last year's Overwatch. What Valve has done with this game, is pretty much take the original Counter Strike, polish and polish it up, add some new features, and release it as this new game. "
                ),
                UserReview(
                    "The Escapist",
                    1347020958,
                    "When it comes down to it, Counter-Strike: Global Offensive may follow the same old formula of gameplay seen in previous versions of Counter-Strike, but it's still a well-rounded tactical shooter."
                ),
                UserReview(
                    "danPgaming",
                    1488198558,
                    "CS:GO with its graphics is moderate, and PC requirements aren't too heavy on the low tier gamer."
                ),
                UserReview(
                    "Ndi",
                    1367497758,
                    "Zero innovation. Same maps, same weapons, only weaker, a bit more graphics and bam, new game."
                )
            )


            return listOf(
                Game(
                    "Fortnite1", "PlayStation 5", "July 21, 2017", 7.0, "fortnite",
                    "Teen", "Epic Games", "Epic Games", "Action",
                    "Fortnite is an online video game developed by Epic Games and released in 2017. It is available in three distinct game mode versions that otherwise share the same general gameplay and game engine: Fortnite",
                    usr1
                ),
                Game(
                    "Counter strike: Global offensive",
                    "Xbox One",
                    "August 21, 2012",
                    9.0,
                    "counter",
                    "Mature",
                    "Valve Corporation",
                    " Valve Corporation",
                    "Tactical shooter",
                    "Counter-Strike: Global Offensive is a 2012 multiplayer tactical first-person shooter developed by Valve and Hidden Path Entertainment. It is the fourth game in the Counter-Strike series.",
                    usr2
                ))
        }
        fun getAll(): List<Game> {
            var usr1 = listOf(
                UserReview(
                    "sword_robber2",
                    1679959758,
                    "This game is a lot better than pubg not too much violence"
                ),
                UserReview(
                    "Acalodo",
                    1678710558,
                    "This Game is Not that violent and non relistic. The game is fun and should be played by every one."
                ),
                UserRating("J G", 1615638558, 2.5),
                UserRating("Lowkstaa", 1615984158, 4.3),
                UserReview(
                    "crispycankles",
                    1647520158,
                    "I don’t necessarily hate the game just the people who made it, it use to be something great but now I can’t even play 5 minutes of it, it’s not fun anymore, they took all the fun aspects from it by adding crappier weapons in it"
                )
            )
            var usr2 = listOf(
                UserRating("OhRly", 1345552158, 4.0),
                UserReview(
                    "Termin8ter",
                    1499689758,
                    "Counter- Strike: Global Offensive is probably the most popular multiplayer game on PC at the moment, along with last year's Overwatch. What Valve has done with this game, is pretty much take the original Counter Strike, polish and polish it up, add some new features, and release it as this new game. "
                ),
                UserReview(
                    "The Escapist",
                    1347020958,
                    "When it comes down to it, Counter-Strike: Global Offensive may follow the same old formula of gameplay seen in previous versions of Counter-Strike, but it's still a well-rounded tactical shooter."
                ),
                UserReview(
                    "danPgaming",
                    1488198558,
                    "CS:GO with its graphics is moderate, and PC requirements aren't too heavy on the low tier gamer."
                ),
                UserReview(
                    "Ndi",
                    1367497758,
                    "Zero innovation. Same maps, same weapons, only weaker, a bit more graphics and bam, new game."
                )
            )


            return listOf(
                Game(
                    "Fortnite", "PlayStation 5", "July 21, 2017", 7.0, "fortnite",
                    "Teen", "Epic Games", "Epic Games", "Action",
                    "Fortnite is an online video game developed by Epic Games and released in 2017. It is available in three distinct game mode versions that otherwise share the same general gameplay and game engine: Fortnite",
                    usr1
                ),
                Game(
                    "Counter strike: Global offensive",
                    "Xbox One",
                    "August 21, 2012",
                    9.0,
                    "counter",
                    "Mature",
                    "Valve Corporation",
                    " Valve Corporation",
                    "Tactical shooter",
                    "Counter-Strike: Global Offensive is a 2012 multiplayer tactical first-person shooter developed by Valve and Hidden Path Entertainment. It is the fourth game in the Counter-Strike series.",
                    usr2
                ),
                Game(
                    "Bunny Factory",
                    "Xbox One",
                    "June 2, 2021",
                    5.5,
                    "bunny",
                    "Everyone",
                    "DillyFrame",
                    "DillyFrame",
                    "Puzzle",
                    "Help bunny to set up factory production by restoring all control blocks. Solve 3D puzzles alone or with friends.",
                    usr1
                ),
                Game(
                    "41 Hours",
                    "PlayStation 5",
                    "May 21, 2021",
                    6.0,
                    "fourty",
                    "Teen",
                    "Texelworks",
                    "Valkyrie Initiative",
                    "Shooter",
                    "41 Hours is a first-person shooter dramatic game that follows the narrative of Ethan, a workaholic scientist in search of his long-lost wife.",
                    usr1
                ),
                Game(
                    "League of legends", "GeForce Now", "October 27, 2009", 6.0, "lol",
                    "Teen", "Riot Games", "Riot Games", "Action",
                    "League of Legends, commonly referred to as League, is a 2009 multiplayer online battle arena video game developed and published by Riot Games. Inspired by Defense of the Ancients, a custom map for Warcraft III, Riot's founders sought to develop a stand-alone game in the same genre.",
                    usr2
                ),

                Game(
                    "Dota", "Windows", "July 9, 2013", 5.5, "dota",
                    "Everyone", "Valve Corporation", "Valve Corporation", "Strategy",
                    "Dota is a series of strategy video games now developed by Valve. The series began in 2003 with the release of Defense of the Ancients, a fan-developed multiplayer online battle arena mod for the video game Warcraft III: Reign of Chaos and its expansion, The Frozen Throne.",
                    usr1
                ),

                Game(
                    "Call of Duty: Black Ops", "PlayStation 3", "November 9, 2010", 8.3, "cod",
                    "Teen", "Treyarch", "Activision", "First-person shooter",
                    "Set in the 1960s during the Cold War, the game's campaign follows CIA operative Alex Mason as he attempts to recall certain memories of combat to locate a numbers station. ",
                    usr2
                ),
                Game(
                    "Battlefield", "Windows", "September 10, 2002", 6.8, "battlefield",
                    "Teen", "Electronic Arts", "Electronic Arts", "First-person shooter",
                    "The series features a particular focus on large maps, teamwork and combined arms warfare. The PC games in the series are mainly focused on online multiplayer.",
                    usr1
                ),
                Game(
                    "Rocket league", "PlayStation 4", "July 7, 2015", 9.3, "rocket",
                    "Everyone", "Psyonix", "Psyonix", "Sports",
                    "Described as \"soccer, but with rocket-powered cars\", Rocket League has up to eight players assigned to each of the two teams, using rocket-powered vehicles to hit a ball into their opponent's goal and score points over the course of a match. ",
                    usr2
                ),
                Game(
                    "Valorant", "Windows", "June 2, 2020", 8.0, "valorant",
                    "Teen", "Riot Games", "Riot Games", "Tactial shooter",
                    "Valorant is a team-based first-person tactical hero shooter set in the near future.[3][4][5][6] Players play as one of a set of Agents, characters based on several countries and cultures around the world.",
                    usr1
                )
            )

        }

        fun GetDetails(title: String): Game {
            var usr1 = listOf(
                UserReview(
                    "sword_robber2",
                    1679959758,
                    "this game is a lot better than pubg not too much violence"
                ),
                UserReview(
                    "Acalodo",
                    1678710558,
                    "This Game is Not that violent and non relistic. The game is fun and should be played by every one."
                ),
                UserRating("J G", 1615638558, 2.5),
                UserRating("Lowkstaa", 1615984158, 4.3),
                UserReview(
                    "crispycankles",
                    1647520158,
                    "I don’t necessarily hate the game just the people who made it, it use to be something great but now I can’t even play 5 minutes of it, it’s not fun anymore, they took all the fun aspects from it by adding crappier weapons in it"
                )
            )
            val g: List<Game> = getAll()
            val game = g.find { game -> title == game.title };
            return game ?: Game(
                "Test",
                "Test",
                "Test",
                0.0,
                "Test",
                "Test",
                "Test",
                "Test",
                "Test",
                "Test",
                usr1
            )
        }
    }
}