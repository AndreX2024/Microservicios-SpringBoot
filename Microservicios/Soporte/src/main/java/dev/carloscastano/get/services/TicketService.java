    package dev.carloscastano.get.services;

    import dev.carloscastano.get.entities.Ticket;
    import dev.carloscastano.get.repository.TicketRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class TicketService implements ITicketService {

        @Autowired
        private TicketRepository repository;

        @Override
        public List<Ticket> getAll() {
            return (List<Ticket>) repository.findAll();
        }

        @Override
        public List<Ticket> obtenerTicketsPorUsuario(Long idUsuario) {
            return repository.findByIdUsuario(idUsuario);
        }
    }