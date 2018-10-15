package com.sahibinden.repository;

import com.sahibinden.domain.Board;
import com.sahibinden.domain.Move;

import java.util.List;

public class MoveRepository extends BaseRepositoryImpl<Move> {

    public List<Move> getExistingMoves(Board board) {

        getEntityManager().getTransaction().begin();

        List<Move> existingMoves = getEntityManager()
                .createQuery("select m from Move m where m.board=:board", Move.class)
                .setParameter("board", board)
                .getResultList();

        getEntityManager().getTransaction().commit();

        return existingMoves;

    }


    public List<Move> getExistingMovesByIdandSequence(long id, int sequence) {

        getEntityManager().getTransaction().begin();

        Board board = new Board();
        board.setId(id);

        List<Move> moves = getEntityManager()
                .createQuery("select m from Move m where m.board=:board and m.sequence<=:sequence", Move.class)
                .setParameter("board", board)
                .setParameter("sequence", sequence)
                .getResultList();


        getEntityManager().getTransaction().commit();

        return null;
    }


}
