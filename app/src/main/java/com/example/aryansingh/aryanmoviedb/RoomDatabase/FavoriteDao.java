package com.example.aryansingh.aryanmoviedb.RoomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Aryan Singh on 8/13/2018.
 */

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM movie")
    List<Long> getAllMovieIds();

    @Delete
    void deleteMovie(Movie movie);

    @Insert
    void addMovie(Movie movie);

    @Insert
    void addTv(Movie movie);

    @Insert
    void addPerson(Movie movie);





//    @Insert
//    void addExpenses(Expense[] expenses);
//
//    @Query("select * from expenses")
//    List<Expense> getExpenses();
//
//    @Query("select * from expenses where amount > :amountA")
//    List<Expense> getExpensesMoreThan100(int amountA);
//
////    @Query("select * from comment where expenseId = :expenseId")
////    List<Comment> getCommentsForExpense(int expenseId);
//
//    @Query("select commentId from expenses_comments where expenseId = :expenseId")
//    int[] getCommentIdsForExpense(int expenseId);
//
//    @Query("select * from comment where id in (:ids)")
//    List<Comment> getCommentsFromIds(int[] ids);

}
