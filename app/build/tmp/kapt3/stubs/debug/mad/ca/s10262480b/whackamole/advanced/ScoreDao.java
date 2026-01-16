package mad.ca.s10262480b.whackamole.advanced;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006\u000f"}, d2 = {"Lmad/ca/s10262480b/whackamole/advanced/ScoreDao;", "", "getLeaderboard", "", "Lmad/ca/s10262480b/whackamole/advanced/LeaderboardEntry;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPersonalBest", "", "userId", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertScore", "", "score", "Lmad/ca/s10262480b/whackamole/advanced/ScoreEntity;", "(Lmad/ca/s10262480b/whackamole/advanced/ScoreEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface ScoreDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertScore(@org.jetbrains.annotations.NotNull()
    mad.ca.s10262480b.whackamole.advanced.ScoreEntity score, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT MAX(score) FROM scores WHERE userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPersonalBest(int userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "\n        SELECT username, MAX(score) as bestScore\n        FROM users INNER JOIN scores ON users.userId = scores.userId\n        GROUP BY users.userId\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLeaderboard(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<mad.ca.s10262480b.whackamole.advanced.LeaderboardEntry>> $completion);
}