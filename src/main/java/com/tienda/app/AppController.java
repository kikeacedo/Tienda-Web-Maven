package com.tienda.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController{

	@Autowired
	private UserRepository user_repository;

	@Autowired
	private ProductoRepository producto_repository;

	@Autowired
	private ImagenRepository imagen_repository;

	@Autowired
	private UserCookie user_cookie;

	@Autowired
	private PedidoRepository pedido_repository;

	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index").addObject("productos", producto_repository.selectOfertas())
				.addObject("cesta_size", user_cookie.size())
				.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
				.addObject("cookie", user_cookie.getLista_productos())
				.addObject("total", calcular_precio_cesta())
				.addObject("sesion_iniciada", user_cookie.isSesion_iniciada());
	}


	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@RequestMapping("/tienda")
	public ModelAndView inicio(@RequestParam String categoria) {
		ModelAndView modelo;
		if(categoria.equals("todo")){
			modelo= new ModelAndView("home").addObject("productos", producto_repository.selectAll())
					.addObject("cesta_size", user_cookie.size())
					.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
					.addObject("cookie", user_cookie.getLista_productos())
					.addObject("categorias", producto_repository.selectCategorias())
					.addObject("total", calcular_precio_cesta())
					.addObject("sesion_iniciada", user_cookie.isSesion_iniciada());
		}else if(categoria.equals("ofertas")){
			modelo= new ModelAndView("home").addObject("productos", producto_repository.selectOfertas())
					.addObject("cesta_size", user_cookie.size())
					.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
					.addObject("cookie", user_cookie.getLista_productos())
					.addObject("categorias", producto_repository.selectCategorias())
					.addObject("total", calcular_precio_cesta())
					.addObject("sesion_iniciada", user_cookie.isSesion_iniciada());
		}else{
			modelo= new ModelAndView("home").addObject("productos", producto_repository.findByCategoria(categoria))
					.addObject("cesta_size", user_cookie.size())
					.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
					.addObject("cookie", user_cookie.getLista_productos())
					.addObject("categorias", producto_repository.selectCategorias())
					.addObject("total", calcular_precio_cesta())
					.addObject("sesion_iniciada", user_cookie.isSesion_iniciada());
		}
		return modelo;

	}

	@RequestMapping("/tienda_name")
	public ModelAndView inicioNombre(@RequestParam String name) {
		System.out.println(name);
		return new ModelAndView("home").addObject("productos", producto_repository.findByNameContaining(name))
				.addObject("cesta_size", user_cookie.size())
				.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
				.addObject("cookie", user_cookie.getLista_productos())
				.addObject("categorias", producto_repository.selectCategorias())
				.addObject("total", calcular_precio_cesta())
				.addObject("sesion_iniciada", user_cookie.isSesion_iniciada());
	}

	@RequestMapping("/show")
	public ModelAndView show(@RequestParam int num_producto){
		Producto producto = producto_repository.findById(num_producto);
		double precio_rebajado = 0;
		if(producto.getOferta() > 0)
			precio_rebajado = producto.getPrice()*(1-producto.getOferta()/100);

		return new ModelAndView("show").addObject("id", producto.getId())
				.addObject("name",producto.getName())
				.addObject("description", producto.getDescription())
				.addObject("price", producto.getPrice())
				.addObject("image", producto.getImage())
				.addObject("oferta",producto.getOferta())
				.addObject("imagenes", imagen_repository.findByProducto(num_producto))
				.addObject("final_price", precio_rebajado)
				.addObject("rebajado", (producto.getOferta()>0)?true:false)
				.addObject("cesta_size", user_cookie.size())
				.addObject("cesta_size", user_cookie.size())
				.addObject("cookie", user_cookie.getLista_productos())
				.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
				.addObject("total", calcular_precio_cesta())
				.addObject("sesion_iniciada", user_cookie.isSesion_iniciada());


	}


	@RequestMapping("/add")
	public ModelAndView insert(@RequestParam int producto){
		user_cookie.addItem(producto);
		return new ModelAndView("home").addObject("productos", producto_repository.selectAll())
				.addObject("cesta_size", user_cookie.size())
				.addObject("cookie", user_cookie.getLista_productos())
				.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
				.addObject("categorias", producto_repository.selectCategorias())
				.addObject("total", calcular_precio_cesta())
				.addObject("sesion_iniciada", user_cookie.isSesion_iniciada());
	}

	@RequestMapping("/addandbuy")
	public ModelAndView insertAndBuy(@RequestParam int producto){
		user_cookie.addItem(producto);
		return new ModelAndView("buy").addObject("productos", coger_cesta(user_cookie.getLista_productos()))
				.addObject("cesta_size", user_cookie.size())
				.addObject("cookie", user_cookie.getLista_productos())
				.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
				.addObject("total", calcular_precio_cesta())
				.addObject("sesion_iniciada", user_cookie.isSesion_iniciada());
	}


	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam int producto){
		user_cookie.removeItem(producto);
		return new ModelAndView("buy").addObject("productos", coger_cesta(user_cookie.getLista_productos()))
				.addObject("cesta_size", user_cookie.size())
				.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
				.addObject("cookie", user_cookie.getLista_productos())
				.addObject("total", calcular_precio_cesta())
				.addObject("sesion_iniciada", user_cookie.isSesion_iniciada());
	}


	@RequestMapping("/cesta")
	public ModelAndView cesta(){
		return new ModelAndView("buy").addObject("productos", coger_cesta(user_cookie.getLista_productos()))
				.addObject("cesta_size", user_cookie.size())
				.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
				.addObject("cookie", user_cookie.getLista_productos())
				.addObject("total", calcular_precio_cesta())
				.addObject("sesion_iniciada", user_cookie.isSesion_iniciada());
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping("/buy")
	public ModelAndView buy(){
		Authentication auth = SecurityContextHolder .getContext().getAuthentication();
		int id = user_repository.findByUsername(auth.getName()).getId();
		
		user_cookie.setSesion_iniciada(true);

		if(user_cookie.size()>0)
			pedido_repository.save(new Pedido(id, user_cookie.getLista_productos(),crearNombre(user_cookie.getLista_productos())));
		user_cookie.vaciar();
		



		return new ModelAndView("pedido").addObject("pedidos", pedido_repository.findByUser(id))
				.addObject("cesta_size", user_cookie.size())
				.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
				.addObject("cookie", user_cookie.getLista_productos())
				.addObject("total", calcular_precio_cesta())
				.addObject("estados",Estado.values())
				.addObject("administrador", false)
				.addObject("user",user_repository.findByUsername(auth.getName()).getName())
				.addObject("sesion_iniciada", user_cookie.isSesion_iniciada());
	}


	@Secured({ "ROLE_ADMIN"})
	@RequestMapping("/admin")
	public ModelAndView admin(@RequestParam Estado estado){
		ModelAndView modelo;
		if(estado == Estado.Todos){
			modelo =  new ModelAndView("pedido").addObject("pedidos", pedido_repository.findAll())
					.addObject("cesta_size", user_cookie.size())
					.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
					.addObject("cookie", user_cookie.getLista_productos())
					.addObject("estados",Estado.values())
					.addObject("total", calcular_precio_cesta())
					.addObject("administrador", true);

		}else{
			modelo = new ModelAndView("pedido").addObject("pedidos", pedido_repository.findByEstado(estado))
					.addObject("cesta_size", user_cookie.size())
					.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
					.addObject("cookie", user_cookie.getLista_productos())
					.addObject("estados",Estado.values())
					.addObject("total", calcular_precio_cesta())
					.addObject("administrador", true);
		}
		return modelo;
	}
	
	@Secured({ "ROLE_ADMIN"})
	@RequestMapping("/cambiarEstado")
	public ModelAndView adminProduct(@RequestParam int id,Estado estado){
		Pedido pedido = pedido_repository.findById(id);
		pedido.setEstado(estado);
		pedido_repository.save(pedido);
		return new ModelAndView("redirect:" + "/admin?estado=Todos");

	}
	
	

	@Secured({ "ROLE_ADMIN"})
	@RequestMapping("/admin_product")
	public ModelAndView adminProduct(@RequestParam String categoria){
		ModelAndView modelo;
		if(categoria.equals("todo")){
			modelo= new ModelAndView("producto").addObject("productos", producto_repository.selectAll())
					.addObject("cesta_size", user_cookie.size())
					.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
					.addObject("cookie", user_cookie.getLista_productos())
					.addObject("categorias", producto_repository.selectCategorias())
					.addObject("total", calcular_precio_cesta());
		}else if(categoria.equals("ofertas")){
			modelo= new ModelAndView("producto").addObject("productos", producto_repository.selectOfertas())
					.addObject("cesta_size", user_cookie.size())
					.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
					.addObject("cookie", user_cookie.getLista_productos())
					.addObject("categorias", producto_repository.selectCategorias())
					.addObject("total", calcular_precio_cesta());
		}else{
			modelo= new ModelAndView("producto").addObject("productos", producto_repository.findByCategoria(categoria))
					.addObject("cesta_size", user_cookie.size())
					.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
					.addObject("cookie", user_cookie.getLista_productos())
					.addObject("categorias", producto_repository.selectCategorias())
					.addObject("total", calcular_precio_cesta());
		}

		return modelo;

	}


	@Secured({ "ROLE_ADMIN"})
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam int id){

		List<Imagen> imagenes_aux = imagen_repository.findByProducto(id);
		String imagenes = "";

		for(Imagen i : imagenes_aux){
			imagenes += i.getUrl() + " ";
		}


		return new ModelAndView("newProduct").addObject("producto", producto_repository.findById(id))
				.addObject("boton", "Editar")
				.addObject("imagenes", imagenes)
				.addObject("edit", true);

	}

	@Secured({ "ROLE_ADMIN"})
	@RequestMapping("/newProduct")
	public ModelAndView newProduct(){
		return new ModelAndView("newProduct").addObject("boton", "Añadir")
				.addObject("edit", false);

	}

	@Secured({ "ROLE_ADMIN"})
	@RequestMapping("/addProduct")
	public ModelAndView addProduct(@RequestParam String name, String descripcion, double price, double oferta, String categoria, String imagen, String imagenes){		
		Producto producto_nuevo = producto_repository.save(new Producto(name, descripcion, price, oferta, imagen, categoria));

		String[] aux = imagenes.split(" ");
		for(int i = 0; i < aux.length; i++){
			imagen_repository.save(new Imagen(producto_nuevo.getId(), aux[i]));
		}
		return new ModelAndView("redirect:" + "/admin_product?categoria=todo");
	}

	@Secured({ "ROLE_ADMIN"})
	@RequestMapping("/editProduct")
	public ModelAndView editProduct(@RequestParam int id, String name, String descripcion, double price, double oferta, String categoria, String imagen, String imagenes){

		Producto producto = producto_repository.findById(id);

		List<Imagen> imagenes_borrar = imagen_repository.findByProducto(id);

		for(Imagen i : imagenes_borrar){
			imagen_repository.delete(i);
		}

		String[] aux = imagenes.split(" ");

		for(int i = 0; i < aux.length; i++){
			imagen_repository.save(new Imagen(id, aux[i]));
		}

		producto.setCategoria(categoria);
		producto.setDescription(descripcion);
		producto.setImage(imagen);
		producto.setName(name);
		producto.setOferta(oferta);
		producto.setPrice(price);
		
		producto_repository.save(producto);
		return new ModelAndView("redirect:" + "/admin_product?categoria=todo");
	}

	@Secured({ "ROLE_ADMIN"})
	@RequestMapping("/remove")
	public ModelAndView removeProduct(@RequestParam int id){

		producto_repository.delete(producto_repository.findById(id));

		return new ModelAndView("redirect:" + "/admin_product?categoria=todo");

	}


	@Secured({ "ROLE_ADMIN"})
	@RequestMapping("/admin_usuario")
	public ModelAndView adminUsuario(@RequestParam String name) {
		System.out.println(name);
		return new ModelAndView("usuarios").addObject("usuarios", user_repository.findByNameContaining(name))
				.addObject("cesta_size", user_cookie.size())
				.addObject("productos_cesta",coger_cesta(user_cookie.getLista_productos()))
				.addObject("cookie", user_cookie.getLista_productos())
				.addObject("categorias", producto_repository.selectCategorias())
				.addObject("total", calcular_precio_cesta());
	}

	@Secured({ "ROLE_ADMIN"})
	@RequestMapping("/addUser")
	public ModelAndView addUser(String username, String name, String apellidos, String password, String direccion, String mail, String telefono){
		GrantedAuthority[] userRoles = { new SimpleGrantedAuthority("ROLE_USER") };
		user_repository.save(new User(username, password, Arrays.asList(userRoles),name,apellidos,direccion,mail,telefono));

		return new ModelAndView("login").addObject("correcto",true);
	}

	@Secured({ "ROLE_ADMIN"})
	@RequestMapping("/newUserForm")
	public ModelAndView newUser(){
		return new ModelAndView("newUser");
	}
	





	private List<Producto> coger_cesta(HashMap<Integer, Integer> map){
		Set<Integer> cesta_productos = map.keySet();
		List<Producto> productos = new ArrayList<Producto>();

		for(int i : cesta_productos){
			productos.add(producto_repository.findById(i));
		}//for

		return productos;
	}

	private  double calcular_precio_cesta(){
		double precio = 0;
		HashMap<Integer, Integer> productos = user_cookie.getLista_productos();
		Set<Integer> keys = productos.keySet();
		for(int i : keys){
			precio += producto_repository.findById(i).calcularPrecio()*productos.get(i);
		}//for
		return precio;
	}

	private String crearNombre(HashMap<Integer, Integer> map){
		String nombre = "";

		Set<Integer> productos = map.keySet();

		for(int i : productos){
			nombre += producto_repository.findById(i).getName() + "  ";
		}

		return nombre;
	}
}//class
