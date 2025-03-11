    package dev.carloscastano.get.services;

    import dev.carloscastano.get.entities.Ticket;

    import java.util.List;

    public interface ITicketService {
        List<Ticket> getAll();
        List<Ticket> obtenerTicketsPorUsuario(Long idUsuario);
    }


