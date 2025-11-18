package com.deckora.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deckora.model.Envio;
import com.deckora.model.Orden;
import com.deckora.model.Pago;
import com.deckora.model.ProductosOrdenes;
import com.deckora.repository.EnvioRepository;
import com.deckora.repository.OrdenRepository;
import com.deckora.repository.PagoRepository;
import com.deckora.repository.ProductosOrdenesRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private ProductosOrdenesRepository productosOrdenesRepository;
    @Autowired
    private ProductosOrdenesService productosOrdenesService;
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired 
    private EnvioRepository envioRepository;

    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    public Orden findById(Long id) {
        return ordenRepository.findById(id).get();
    }

    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    // Query 1
    public List<Orden> buscarPorEstadoYUsuario(Integer idEstado, Integer idUsuario) {
        return ordenRepository.buscarPorEstadoYUsuario(idEstado, idUsuario);
    }

    // Query 2
    public List<Orden> buscarPorPagoYProducto(Integer idPago, Integer idProducto) {
        return ordenRepository.buscarPorPagoYProducto(idPago, idProducto);
    }
    // Query 3
    public List<Orden> buscarOrdenesPorCantidadDeProductoYUsuario(Integer idUsuario, Integer cantidadProducto) {
        return ordenRepository.buscarOrdenesPorCantidadDeProductoYUsuario(idUsuario, cantidadProducto);
    }
    // Query 4
    public List<Orden> buscarPorProductoYUsuario(Integer idProducto, Integer idUsuario) {
        return ordenRepository.buscarPorProductoYUsuario(idProducto, idUsuario);
    }

    // Eliminar x cascada (para borrar relacion con categoria)
    public void delete(Long id) {

        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        List<ProductosOrdenes> productosOrdenes = productosOrdenesRepository.findByOrden(orden);
        for (ProductosOrdenes productosYOrdenes : productosOrdenes) {
            productosOrdenesService.delete(Long.valueOf(productosYOrdenes.getId()));
        }

        ordenRepository.delete(orden);
    }

    //Metodos Nuevos 1
    public List<Orden> buscarEntreFechas(String fechaInicio, String fechaFinal){
        return ordenRepository.findByFechaBetween(fechaInicio,fechaFinal);
    }

    //Metodos Nuevos 2
    public List<Orden> buscarPorPagoYDelivery(Integer idPago, Integer idEnvio){
        Pago pago = pagoRepository.findById(Long.valueOf(idPago))
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        Envio delivery = envioRepository.findById(Long.valueOf(idEnvio))
            .orElseThrow(() -> new RuntimeException("Envio no encontrado"));

        return ordenRepository.findByPagoAndDelivery(pago, delivery);
    } 

    public Orden patchOrden(Long id, Orden parcialOrder) {
        Optional<Orden> ordenOpcional = ordenRepository.findById(id);
        if (ordenOpcional.isPresent()) {

            Orden ordenActualizar = ordenOpcional.get();

            if (parcialOrder.getFecha() != null) {
                ordenActualizar.setFecha(parcialOrder.getFecha());
            }
            if (parcialOrder.getTotal() != null) {
                ordenActualizar.setTotal(parcialOrder.getTotal());
            }
            return ordenRepository.save(ordenActualizar);
        } else {
            return null;
        }
    }
}
