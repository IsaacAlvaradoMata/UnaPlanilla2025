/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.unaplanilla2025.service;

import cr.ac.una.unaplanilla2025.model.Empleado;
import cr.ac.una.unaplanilla2025.model.EmpleadoDto;
import cr.ac.una.unaplanilla2025.util.EntityManagerHelper;
import cr.ac.una.unaplanilla2025.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josue_5njzopn
 */
public class EmpleadoService {
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et; 
    
    public Respuesta getEmpleado(Long id) {
        try {
            Query qryEmpleado = em.createNamedQuery("Empleado.findById", Empleado.class);
            qryEmpleado.setParameter("id", id);
            EmpleadoDto empleadoDto = new EmpleadoDto((Empleado) qryEmpleado.getSingleResult());
            return new Respuesta(true, "", "", "Empleado", empleadoDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un empleado con el id ingresado.", "getEmpleado NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el empleado.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el empleado.", "getEmpleado NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo el empleado [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el empleado.", "getEmpleado " + ex.getMessage());
        }
    }
    
    
    public Respuesta guardarEmpleado(EmpleadoDto empleadoDto) {
        try {
           et = em.getTransaction();
           et.begin();
           
           Empleado empleado;
           if (empleadoDto.getId() != null && empleadoDto.getId( ) > 0){
               empleado = em.find(Empleado.class, empleadoDto.getId());
               if(empleado == null){
                               return new Respuesta(false, "No se encontro el empleado a modificar.", "guardarEmpleado  NoResultException" );

               }
               empleado.actualizar(empleadoDto);
               empleado = em.merge(empleado);
           
           } else {
           empleado = new Empleado(empleadoDto);
           em.persist(empleado);
           
           }
       
           et.commit();
           return new Respuesta(true, "", "", "Empleado", new EmpleadoDto(empleado));

        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error guardando el empleado", ex);
            return new Respuesta(false, "Error guardando el empleado.", "guardarEmpleado " + ex.getMessage());
        }
    }
    
    
    public Respuesta eliminarEmpleado(Long id) {
        try {
           et = em.getTransaction();
           et.begin();
           
           Empleado empleado;
           if (id != null && id > 0){
               empleado = em.find(Empleado.class, id);
               if(empleado == null){
                               return new Respuesta(false, "No se encontro el empleado a eliminar.", "eliminarEmpleado  NoResultException" );

               }
              
               em.remove(empleado);
           
           } else {
                return new Respuesta(false, "favor consultar el empleado a eliminar.", "" );

           
           }
       
           et.commit();
           return new Respuesta(true, "", "");

        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error eliminando el empleado", ex);
            return new Respuesta(false, "Error eliminando el empleado.", "eliminandoEmpleado " + ex.getMessage());
        }
    }

    public Respuesta filtrarEmpleados(String cedula, String nombre) {
        try {
            Query qry = em.createNamedQuery("Empleado.findByCedulaAndNombre", Empleado.class);
            qry.setParameter("cedula", (cedula == null || cedula.isBlank()) ? null : cedula);
            qry.setParameter("nombre", (nombre == null || nombre.isBlank()) ? null : nombre);

            List<Empleado> empleados = qry.getResultList();
            List<EmpleadoDto> empleadosDto = empleados.stream().map(EmpleadoDto::new).toList();

            return new Respuesta(true, "", "", "Empleados", empleadosDto);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error filtrando empleados", ex);
            return new Respuesta(false, "Error filtrando empleados.", "filtrarEmpleados " + ex.getMessage());
        }
    }


}
