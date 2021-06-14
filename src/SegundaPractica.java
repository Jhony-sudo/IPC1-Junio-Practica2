package src;
import java.util.Scanner;

    public class SegundaPractica {

    public static void main(String[] args) {
        SegundaPractica Iniciar = new SegundaPractica();
    
    } 
    
    Scanner leer = new Scanner(System.in);
    static int CANTIDAD_MAXIMA = 30;
    int ContadorCliente = 0;
    int ContadorPelicula = 0;
    int ContadorTabla = 0;
    int ContadorTablaGeneral = 0;
    int ContadorCategorias = 0;
    int Recorrido = 1;
    String[] CategoriasUnicas = new String[CANTIDAD_MAXIMA];
    
    //Variables Globales Cliente
    int [] Telefono = new int[CANTIDAD_MAXIMA];
    String[] NombreCliente = new String[CANTIDAD_MAXIMA];
    int [] IdCliente = new int[CANTIDAD_MAXIMA];
    boolean [] EstadoCliente = new boolean [CANTIDAD_MAXIMA];
    
    //Variables globales Peliculas
    String[] NombrePeliculas = new String[CANTIDAD_MAXIMA];
    int [] IdPelicula = new int[CANTIDAD_MAXIMA];
    int [] Anio = new int[CANTIDAD_MAXIMA];
    String[] Categoria = new String[CANTIDAD_MAXIMA];
    boolean [] EstadoPelicula = new boolean [CANTIDAD_MAXIMA];
    
    //Variables globales Prestamo Peliculas
    int [][] DiasPrestamo = new int[CANTIDAD_MAXIMA][3];
    String[][] Devolucion  = new String[CANTIDAD_MAXIMA][2];
    
    //Variables Reportes
    int [][] MasPrestados = new int[CANTIDAD_MAXIMA][2];
    
    
    public SegundaPractica(){
       
        int Seleccion;
        do{
            System.out.println("--MEMORABILIA--");
            System.out.println("---Mennu--");
            System.out.println("1. Prestar Pelicula");
            System.out.println("2. Devolver Pelicula");
            System.out.println("3. Mostrar Peliculas");
            System.out.println("4. Crear Peliculas");
            System.out.println("5. Ordenar Peliculas");
            System.out.println("6. Ingresar Clientes Nuevos");
            System.out.println("7. Mostrar Clientes");
            System.out.println("8. Reportes");
            System.out.println("9. Salir");
            Seleccion = leer.nextInt();
            switch(Seleccion){
                case 1:
                    PrestarPelicula();
                    break;
                case 2:
                    DevolverPelicula();
                    break;
                case 3:
                    MostrarPeliculas();
                    break;
                case 4:
                    CrearPelicula();
                    break;
                case 5:
                    Ordenar(NombrePeliculas);
                    break;
                case 6:
                    NuevoCliente();
                    break;
                case 7:
                    MostrarClientes();
                    break;
                case 8:
                    Reportes();
                    
                    
                    break;
                case 9:
                    System.out.println("Adios Amigo");
                    break;
                default:
                    System.out.println("Ingrese una Opcion Correcta");
                    break;
                    
            }
        }while(Seleccion != 9);
    
    }
    
    public void PrestarPelicula(){
        int Id;
        System.out.println("Ingrese el Id del Cliente");
        Id = leer.nextInt();
        if(RevisarExistencia(IdCliente,Id,ContadorCliente)){
             if(!EstadoCliente[getPosicion(Id)]){
                int Aceptar;
                System.out.println("Solo puedes prestar una pelicula De Acuerdo ?");
                System.out.println("1.De Acuerdo      2.Salir");
                Aceptar = leer.nextInt();
                if(Aceptar == 1 ){
                    int Seleccion;
                    int Dias;
                    
                    if(PeliculasDisponibles(true)){
                    Seleccion = leer.nextInt();
                    System.out.println("Cuantos dias la prestaras ?");
                    Dias = leer.nextInt();
                    EstadoPelicula[Seleccion-1] = false;
                    EstadoCliente[getPosicion(Id)] = true;
                    LlenarPrestamo(NombreCliente[getPosicion(Id)],NombrePeliculas[Seleccion-1]);
                    LlenarTablaGeneral(Id,IdPelicula[Seleccion-1],Dias);
                        
                    
                        Llenar();
                        for(int i = 0;i<ContadorTabla;i++){
                            if(IdPelicula[Seleccion-1] == MasPrestados[i][1]){
                            MasPrestados[i][0]++;
                            }
                        }
                       
                    }
                    else{
                        System.out.println("No hay peliculas Disponibles"); //Siuu
                    }
                }
            }
             else {
            System.out.println("Ya tienes prestada una Pelicula");
            }
        }
        else{
            System.out.println("El id no existe");
        }
        
    }
    
    public void DevolverPelicula(){
        int seleccion;
        System.out.println("Peliculas prestadas");
        MostrarTabla(Devolucion);
        seleccion = leer.nextInt();
        int Posicion = getPosicion(Devolucion[seleccion-1][0]);
        int PosicionP = getPosicionPelicula(Devolucion[seleccion-1][1]);
        EstadoPelicula[PosicionP] = true;
        EstadoCliente[Posicion] = false;
        AcomodarPrestamos(seleccion-1);
        ContadorTablaGeneral--; 
        
    }
    public void AcomodarPrestamos(int Nombre){
        Devolucion[Nombre][0] = Devolucion[ContadorTablaGeneral-1][0];
        Devolucion[Nombre][1] = Devolucion[ContadorTablaGeneral-1][1];
    }
    
    public void MostrarPeliculas(){
        System.out.println("Id     Nombre           A;io          Categoria          Estado");
        for(int i = 0; i<ContadorPelicula;i++){
            String Estado = "Disponible";
            if(!EstadoPelicula[i]){
                Estado = "No Disponible";
            }
           // System.out.println(IdPelicula[i] + "    " + NombrePeliculas[i]+ "            " + Anio[i] + "  " + Categoria[i] +   "     " + EstadoPelicula[i]);    
            System.out.print(IdPelicula[i]+"    ");
            System.out.print(NombrePeliculas[i]+"         ");
            System.out.print(Anio[i]+"              ");
            System.out.print(Categoria[i]+"        ");
            System.out.print(Estado +"\n");
        }

        
    }
    
    public boolean PeliculasDisponibles(boolean Respuesta){
        System.out.println("Peliculas Disponibles");
        boolean Resultado = false;
        
        for(int i = 0;i<ContadorPelicula;i++){
            if(EstadoPelicula[i] == Respuesta){
                System.out.println((i+1) +"  "+ NombrePeliculas[i]);
                Resultado = true;
            }
        }
        return Resultado;
    }
    
    public void CrearPelicula(){
        int Id;
        boolean Resultado;
        if(ContadorPelicula<=29){
        System.out.println("Ingrese el Nombre de la Pelicula"); 
        leer.skip("\n");   
        NombrePeliculas[ContadorPelicula] = leer.nextLine();
        System.out.println("Ingrese el A;o de la Pelicula");    
        Anio[ContadorPelicula] = leer.nextInt(); 
        System.out.println("Ingese el identificador de la Pelicula");
        do{
        Id = leer.nextInt();
        Resultado = RevisarExistencia(IdPelicula,Id,ContadorPelicula);
           
        } while(Resultado != false);
        IdPelicula[ContadorPelicula] = Id;
        System.out.println("Ingrese la categoria de la Pelicula");
         leer.skip("\n");  
        Categoria[ContadorPelicula] = leer.nextLine();
        EstadoPelicula[ContadorPelicula] = true;
        ContadorPelicula++;
        
        }
        else{
            System.out.println("Ya no se puede ingresar mas Peliculas");
        }
        
    }
    
    public void Ordenar(String[] array){
        String selec;
        int pos = 0;
        int Id;
        boolean Estado;
        int Ani;
        String Cate;
        for (int i = 0; i < ContadorPelicula; i++) {
            selec = array[i];
            pos = i;

            for (int j = i+1; j < ContadorPelicula; j++) {
                if (selec.charAt(0) > array[j].charAt(0)){
                    selec = array[j];
                    pos = j;
                }
                
            }


            array[pos] = array[i];
            array[i]  = selec;
            
            Id = IdPelicula[pos];
            IdPelicula[pos] = IdPelicula[i];  
            IdPelicula[i] = Id;
            
            Estado = EstadoPelicula[pos];
            EstadoPelicula[pos] = EstadoPelicula[i]; 
            EstadoPelicula[i] = Estado;
            
            Ani = Anio[pos];
            Anio[pos] = Anio[i];        
            Anio[i] = Ani;
            
            Cate = Categoria[pos];
            Categoria[pos] = Categoria[i];
            Categoria[i] = Cate;
           
           
            
        }
        

    }
    
    public void LlenarTablaGeneral(int IdCl,int IdPel,int Dias){
        DiasPrestamo[ContadorTabla][0] = IdCl; 
        DiasPrestamo[ContadorTabla][1] = IdPel; 
        DiasPrestamo[ContadorTabla][2] = Dias;
        
        ContadorTabla++;
    }
    public void MostrarTabla(String [][] Arr){
        System.out.println("Nombre              Nombre Pelicula");
        for(int i=0;i<ContadorTablaGeneral;i++){

                System.out.println((i+1) + "    " + Devolucion[i][0]+ "         " + Devolucion[i][1]);
                
        }
    }
    public void LlenarPrestamo(String Nombre,String NombrePelicula){
        Devolucion[ContadorTablaGeneral][0] = Nombre;
        Devolucion[ContadorTablaGeneral][1] = NombrePelicula;
        ContadorTablaGeneral++;
    }
    
    public void NuevoCliente(){
        int Id;
        boolean Resultado;
        if(ContadorCliente<=29){
        System.out.println("Ingrese el Nombre del cliente"); 
        leer.skip("\n");   
        NombreCliente[ContadorCliente] = leer.nextLine();
        System.out.println("Ingrese el No de Telefono del Cliente");    
        Telefono[ContadorCliente] = leer.nextInt(); 
        System.out.println("Ingese el identificador del Cliente");
        do{
        Id = leer.nextInt();
        Resultado = RevisarExistencia(IdCliente,Id,ContadorCliente);
           
        } while(Resultado != false);
        IdCliente[ContadorCliente] = Id;
        EstadoCliente[ContadorCliente] = false;
        ContadorCliente++;
        
        }
        else{
            System.out.println("Ya no se puede ingresar mas clientes");
        }
  
    }
    
    public void MostrarClientes(){
        
        System.out.println("Id     Nombre           Telefono");
        for(int i = 0; i<ContadorCliente;i++){
            String Estado = "Prestado" ;
            if(!EstadoCliente[i]){
                Estado = "No tiene prestado";
            }
            
            System.out.println(IdCliente[i] + "    " + NombreCliente[i]+ "            " + Telefono[i] + "  " + Estado);    
        }
        
    }
    
    public void Reportes(){
        int Seleccion;
        int Contador;
        String CategoriaEspec;
        do{
        System.out.println("1. Peliculas por categoria");
        System.out.println("2. Categoria en Especifico");
        System.out.println("3. Reporte cantidad de veces prestadas");
        System.out.println("4. Pelicula mas prestada y menos prestadas");
        Seleccion = leer.nextInt();
        switch(Seleccion){
            case 1:
                    RevisarCategorias();
                    for(int i = 0;i<Recorrido;i++){
                        System.out.println("Categoria: " + CategoriasUnicas[i]);
                        for(int a = 0;a<ContadorPelicula;a++){
                        if(CategoriasUnicas[i].equals(Categoria[a])){
                            System.out.println(NombrePeliculas[a]);
                        }
                        }
                    }
                    
                break;
            case 2:
                System.out.println("Ingrese una categoria en especifico");
                CategoriaEspec = leer.next();
                for(int i =0;i<ContadorPelicula;i++){
                    if(CategoriaEspec.equals(Categoria[i])){
                        System.out.println(NombrePeliculas[i]);
                    }
                }
                break;
            case 3:
                System.out.println("Reporte de prestamos");
                System.out.println("ID Pelicula " + "      ID Cliente que presto" + "     Dias prestados");
                for(int i = 0;i<ContadorTabla;i++){
                    System.out.println("  "+DiasPrestamo[i][1] + "                          " + DiasPrestamo[i][0] + "                        " + DiasPrestamo[i][2]);
                     
                        
                     
                }
                
                
                break;
            case 4:
                System.out.println("Pelicula mas prestada");
                System.out.println("ID " +  "      Cantidad de veces" );
                for(int i=0;i<ContadorTabla;i++){
                    System.out.println(MasPrestados[i][1] + "      "   +  MasPrestados[i][0]);
                }
                
                break;

            default:
                break;
        }
        } while(Seleccion <=5);
        
        
        
    }
    public void RevisarCategorias(){
   
    boolean Repetido = true;
    
    
    CategoriasUnicas[0] = Categoria[0];
    for(int i = 1;i<ContadorPelicula;i++){
       boolean Res = false;
       
       for(int a = 0;Res == false & a<Recorrido;a++){
           
           Res = Categoria[i].equals(CategoriasUnicas[a]);
           
           if(Res == true){
               Repetido = true;
           }
           else{
           Repetido = false;
           }
           
           
           
       }
        if(Repetido == true){
               
    }
        if(Repetido == false){
               CategoriasUnicas[Recorrido] = Categoria[i];
               ContadorCategorias++;
               Recorrido ++;
    }
    }
        
        
    }
    public boolean RevisarExistencia(int[] Arr,int Codigo,int Contador){
        boolean Estado = false;
        for(int i = 0;i<Contador;i++){
            if(Codigo == Arr[i] ){
                System.out.println("Este ID existe");
                Estado = true;
            }
               
        }
        return Estado;
    }
    
    public int getPosicion(int ID){
        int Posicion = 0;
        for(int i = 0;i<ContadorCliente;i++){
            if(ID == IdCliente[i]){
                Posicion = i ;
            }
        }
        
        return Posicion;
    }
    public int getPosicion(String Nombre){
        int Posicion = 0;
        for(int i = 0;i<ContadorCliente;i++){
            if(Nombre == NombreCliente[i]){
                Posicion = i;
            }
        }
        return Posicion;
    }
    
     public int getPosicionPelicula(String Nombre){
        int Posicion = 0;
        for(int i = 0;i<ContadorPelicula;i++){
            if(Nombre == NombrePeliculas[i]){
                Posicion = i;
            }
        }
        return Posicion;
    }
    
     
     public void MayorRepetido(){
      int [][] Repetido = new int[30][2];
      int MasRepetido;
        for(int i = 0;i<ContadorTabla;i++){
         
         MasRepetido = DiasPrestamo[i][1]; 
     }
     }
     
     public void Llenar(){
         int Contador = 1;
         for(int i = 0;i<ContadorPelicula;i++){
        MasPrestados[i][1] = DiasPrestamo[i][1];
        MasPrestados[i][0] = Contador;
         }
     }
     
         
        
}
