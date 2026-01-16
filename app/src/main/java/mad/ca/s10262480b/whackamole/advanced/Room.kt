package mad.ca.s10262480b.whackamole.advanced


import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val username: String,
    val password: String
)


@Entity(tableName = "scores",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["userId"], childColumns = ["userId"], onDelete = ForeignKey.CASCADE)],
    indices = [
        Index(value = ["userId"])
    ])
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val score: Int,
    val timestamp: Long
)


@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUser(username: String): UserEntity?

}


@Dao
interface ScoreDao{
    @Insert
    suspend fun insertScore(score: ScoreEntity)

    @Query("SELECT MAX(score) FROM scores WHERE userId = :userId")
    suspend fun getPersonalBest(userId: Int): Int?

    @Query("""
        SELECT username, MAX(score) as bestScore
        FROM users INNER JOIN scores ON users.userId = scores.userId
        GROUP BY users.userId
    """)
    suspend fun getLeaderboard(): List<LeaderboardEntry>
}


data class LeaderboardEntry(
    val username: String,
    val bestScore: Int
)


@Database(entities = [UserEntity::class, ScoreEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun scoreDao(): ScoreDao
}

