package com.sahibinden.controller;

import com.sahibinden.domain.Board;
import com.sahibinden.domain.Move;
import com.sahibinden.domain.Word;
import com.sahibinden.exception.GameException;
import com.sahibinden.model.BaseResponse;
import com.sahibinden.model.Content;
import com.sahibinden.model.ResponseModel;
import com.sahibinden.service.GameService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("game")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
public class GameController extends BaseController {

    @Inject
    private GameService gameService;

    @GET
    @Path("show/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public void showBoard(@PathParam("id") long id) {
        gameService.drawBoard(id);
    }

    @POST
    public BaseResponse create(Board board) {
        return createSaveEntityResponse(gameService.createBoard(board));
    }

    @POST
    @Path("{id}")
    public ResponseModel play(@PathParam("id") long id, List<Move> moves) throws Exception {
        return gameService.play(id, moves);
    }

    @GET
    @Path("{id}")
    public List<Word> getWords(@PathParam("id") long id) {
        return gameService.getWords(id);
    }

    /**
     * Servis 4: ? getBoardContent(Long boardId, Integer sequence) -> 15x15 hücre üzerinde hangi harflerin olduğu
     * görüntülenebilecek. sequence = 0 tahtanın boş hali, 1 ilk turda eklenen kelime(ler) sonrası, 2 ikinci turda
     * eklenen kelime(ler) sonrası, vs şeklinde.
     *
     * @param id
     * @param sequence
     * @return
     */

    @GET
    @Path("{id}/sequence/{sequence}")
    public Content getBoardContent(@PathParam("id") long id, @PathParam("sequence") int sequence) {
        return gameService.getBoardContent(id, sequence);

    }


    @DELETE
    @Path("{id}")
    public Response setStatus(@PathParam("id") long id) {
        return gameService.setStatus(id);
    }


    @GET
    @Path("test/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public void test() throws GameException {
        throw new GameException("test");
    }


}
