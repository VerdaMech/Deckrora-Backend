package com.deckora;

import com.deckora.model.Categoria;
import com.deckora.model.Envio;
import com.deckora.model.EstadoOrden;
import com.deckora.model.Orden;
import com.deckora.model.Pago;
import com.deckora.model.Producto;
import com.deckora.model.ProductosCategorias;
import com.deckora.model.ProductosOrdenes;
import com.deckora.model.TipoUsuario;
import com.deckora.model.Usuario;

import com.deckora.repository.CategoriaRepository;
import com.deckora.repository.EnvioRepository;
import com.deckora.repository.EstadoOrdenRepository;
import com.deckora.repository.OrdenRepository;
import com.deckora.repository.PagoRepository;
import com.deckora.repository.ProductoRepository;
import com.deckora.repository.ProductosCategoriasRepository;
import com.deckora.repository.ProductosOrdenesRepository;
import com.deckora.repository.TipoUsuarioRepository;
import com.deckora.repository.UsuarioRepository;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Date;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    CategoriaRepository categoriaRepo;
    @Autowired
    EnvioRepository envioRepo;
    @Autowired
    EstadoOrdenRepository estadoOrdenRepo;
    @Autowired
    OrdenRepository ordenRepo;
    @Autowired
    PagoRepository pagoRepo;
    @Autowired
    ProductoRepository productoRepo;
    @Autowired
    ProductosCategoriasRepository productosCategoriasRepo;
    @Autowired
    ProductosOrdenesRepository productosOrdenesRepo;
    @Autowired
    TipoUsuarioRepository tipoUsuarioRepo;
    @Autowired
    UsuarioRepository usuarioRepo;


    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd"); // Para que se pueda usar fechas en los string x-x

        // Generar Categorias
        for (int i = 0; i < 5; i++) {
            Categoria categoria = new Categoria();
            categoria.setId(i + 1);
            categoria.setDescripcion(faker.commerce().department());
            categoria.setProductos(new ArrayList<>());

            categoriaRepo.save(categoria);
        }

        // Generar Productos
        for (int i = 0; i < 5; i++) {
            Producto producto = new Producto();
            producto.setId(i + 1);
            producto.setNombre_producto(faker.commerce().productName());
            producto.setPrecio(faker.number().numberBetween(5000, 1000000));
            producto.setCantidad(faker.number().numberBetween(1, 1000));
            producto.setCategorias(new ArrayList<>());
            producto.setOrdenes(new ArrayList<>());

            productoRepo.save(producto);
        }

        // Lista de productos y categorias para poder usar más abajo
        List<Producto> productos = productoRepo.findAll();
        List<Categoria> categorias = categoriaRepo.findAll();

        // Generar producto categorias
        for (int i = 0; i < 5; i++) {
            ProductosCategorias productosCategorias = new ProductosCategorias();
            productosCategorias.setId(i + 1L);
            productosCategorias.setProducto(productos.get(random.nextInt(productos.size())));
            productosCategorias.setCategoria(categorias.get(random.nextInt(categorias.size())));

            productosCategoriasRepo.save(productosCategorias);
        }

        ArrayList<String> listaTipoUsuarios = new ArrayList();
        listaTipoUsuarios.add("SuperAdmin");
        listaTipoUsuarios.add("Admin");
        listaTipoUsuarios.add("Cliente");
        listaTipoUsuarios.add("Tester");
        // Generar tipos de Usuarios
        for (int i = 0; i < 4; i++) {
            TipoUsuario tipoUsuario = new TipoUsuario();
            tipoUsuario.setId(i + 1);
            tipoUsuario.setDescripcion(listaTipoUsuarios.get(i)); 

            tipoUsuarioRepo.save(tipoUsuario);
        }

        // Generar envios
        for (int i = 0; i < 5; i++) {
            Envio envio = new Envio();
            envio.setId(i + 1);
            envio.setNombre_envio(faker.name().firstName()+" Correos");

            envioRepo.save(envio);
        }

        ArrayList<String> listaPagos = new ArrayList();
        listaPagos.add("Debito");
        listaPagos.add("Credito");
        listaPagos.add("Efectivo");
        // Generar pagos
        for (int i = 0; i < 3; i++) {
            Pago pago = new Pago();
            pago.setId(i + 1);
            pago.setDescripcion(listaPagos.get(i));
            pagoRepo.save(pago);
        }

        ArrayList<String> listaEstadoOrden = new ArrayList();
        listaEstadoOrden.add("En proceso");
        listaEstadoOrden.add("Finalizado");
        listaEstadoOrden.add("Cancelado");
        // Generar Estado de ordenes (Deberia ser algo como: en proceso, en preparacion,
        // etc. Revisar como hacer eso con datafaker)
        for (int i = 0; i < 3; i++) {
            EstadoOrden estadoOrden = new EstadoOrden();
            estadoOrden.setId(i + 1);
            estadoOrden.setDescripcion(listaEstadoOrden.get(i));

            estadoOrdenRepo.save(estadoOrden);
        }

        // Lista de tipos de usuario para usar más abajo
        List<TipoUsuario> tiposUsuarios = tipoUsuarioRepo.findAll();

        // Generar usuarios
        for (int i = 0; i < 7; i++) {
            Usuario usuario = new Usuario();
            usuario.setId(i + 1);
            usuario.setContrasenia(faker.leagueOfLegends().champion());
            usuario.setRun(faker.idNumber().valid());
            usuario.setNombre(faker.name().firstName());
            usuario.setApellido(faker.name().lastName());
            usuario.setCorreo(faker.internet().emailAddress());
            usuario.setDireccion(faker.address().fullAddress());
            usuario.setNumero_telefono(faker.number().numberBetween(100000000L, 999999999L));
            usuario.setTipoUsuario(tiposUsuarios.get(random.nextInt(tiposUsuarios.size())));

            usuarioRepo.save(usuario);
        }

        // Listas para usar más abajo
        List<Usuario> usuarios = usuarioRepo.findAll();
        List<Pago> pagos = pagoRepo.findAll();
        List<EstadoOrden> estadosOrdenes = estadoOrdenRepo.findAll();
        List<Envio> envios = envioRepo.findAll();

        // Generar Ordenes
        for (int i = 0; i < 5; i++) {
            Orden orden = new Orden();
            orden.setId(i + 1);
            Date fechaFaker = faker.date().past(30, TimeUnit.DAYS); // Lo marca como error, pero ta joya
            orden.setFecha(forma.format(fechaFaker));
            orden.setTotal(faker.number().numberBetween(5000, 1000000));

            orden.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
            orden.setPago(pagos.get(random.nextInt(pagos.size())));
            orden.setEstadoOrden(estadosOrdenes.get(random.nextInt(estadosOrdenes.size())));
            orden.setDelivery(envios.get(random.nextInt(envios.size())));
            orden.setProductos(new ArrayList<>());

            ordenRepo.save(orden);
        }

        // Lista para usar más abajo
        List<Orden> ordenes = ordenRepo.findAll();

        // Generar Productos oredenes
        for (int i = 0; i < 5; i++) {
            ProductosOrdenes productosOrdenes = new ProductosOrdenes();
            productosOrdenes.setId(i + 1);
            productosOrdenes.setCantidad_producto(faker.number().numberBetween(1, 10));
            productosOrdenes.setProducto(productos.get(random.nextInt(productos.size())));
            productosOrdenes.setOrden(ordenes.get(random.nextInt(ordenes.size())));

            productosOrdenesRepo.save(productosOrdenes);
        }

    }
}
