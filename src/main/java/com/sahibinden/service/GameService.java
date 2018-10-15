package com.sahibinden.service;

import com.sahibinden.domain.Board;
import com.sahibinden.domain.Move;
import com.sahibinden.domain.Word;
import com.sahibinden.model.Content;
import com.sahibinden.model.ResponseModel;

import javax.ws.rs.core.Response;
import java.util.List;

public interface GameService {

    Long createBoard(Board board);

    ResponseModel play(long id, List<Move> moves) throws Exception;

    void drawBoard(Long id);

    List<Word> getWords(long id);

    Content getBoardContent(long id, int sequence);

    Response setStatus(long id);

}
