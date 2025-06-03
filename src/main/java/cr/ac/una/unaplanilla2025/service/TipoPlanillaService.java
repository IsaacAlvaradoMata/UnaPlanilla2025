package cr.ac.una.unaplanilla2025.service;

import cr.ac.una.unaplanilla2025.model.Empleado;
import cr.ac.una.unaplanilla2025.model.EmpleadoDto;
import cr.ac.una.unaplanilla2025.model.TipoPlanilla;
import cr.ac.una.unaplanilla2025.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla2025.util.EntityManagerHelper;
import cr.ac.una.unaplanilla2025.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author josue_5njzopn
 */
public class TipoPlanillaService {

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta getTipoPlanilla(Long id) {
        try {
            Query qryTipoPlanilla = em.createNamedQuery("Tipoplanilla.findByTplaId", TipoPlanilla.class);
            qryTipoPlanilla.setParameter("id", id);
            TipoPlanillaDto tipoPlanillaDto = new TipoPlanillaDto((TipoPlanilla) qryTipoPlanilla.getSingleResult());
            return new Respuesta(true, "", "", "TipoPlanilla", tipoPlanillaDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe una planilla con el id ingresado.", "getTipoPlanilla NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Ocurrió un error al consultar la planilla.", ex);
            return new Respuesta(false, "Ocurrió un error al consultar la planilla.", "getTipoPlanilla NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error obteniendo la planilla [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo la planilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        try {
            et = em.getTransaction();
            et.begin();

            TipoPlanilla tipoPlanilla;
            if (tipoPlanillaDto.getId() != null && tipoPlanillaDto.getId() > 0) {
                tipoPlanilla = em.find(TipoPlanilla.class, tipoPlanillaDto.getId());
                if (tipoPlanilla == null) {
                    et.rollback();
                    return new Respuesta(false, "No se encontró la planilla a modificar.", "guardarTipoPlanilla NoResultException");
                }
                tipoPlanilla.actualizar(tipoPlanillaDto);
                for(EmpleadoDto empleado: tipoPlanillaDto.getEmpleadosEliminados()) {
                    tipoPlanilla.getEmpleados().remove(new Empleado(empleado.getId()));
                }

                if(!tipoPlanilla.getEmpleados().isEmpty()) {
                    for(EmpleadoDto empleado: tipoPlanillaDto.getEmpleados()) {
                        if(empleado.getModificado()){
////                            Empleado empleado = em.find(Empleado.class, empleadoDto.getId());
//                            empleado.getTipoPlanillaList().add(tipoPlanilla);
//                            tipoPlanilla.getEmpleados().add(empleado);
                        }
                    }
                }

                tipoPlanilla = em.merge(tipoPlanilla);
            } else {
                tipoPlanilla = new TipoPlanilla(tipoPlanillaDto);
                em.persist(tipoPlanilla);
            }

            et.commit();
            return new Respuesta(true, "", "", "TipoPlanilla", new TipoPlanillaDto(tipoPlanilla));

        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error guardando la planilla", ex);
            return new Respuesta(false, "Error guardando la planilla.", "guardarTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta eliminarTipoPlanilla(Long id) {
        try {
            et = em.getTransaction();
            et.begin();

            TipoPlanilla tipoPlanilla;
            if (id != null && id > 0) {
                tipoPlanilla = em.find(TipoPlanilla.class, id);
                if (tipoPlanilla == null) {
                    return new Respuesta(false, "No se encontró la planilla a eliminar.", "eliminarTipoPlanilla NoResultException");
                }
                em.remove(tipoPlanilla);
            } else {
                return new Respuesta(false, "Favor consultar la planilla a eliminar.", "");
            }

            et.commit();
            return new Respuesta(true, "", "");

        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error eliminando la planilla", ex);
            return new Respuesta(false, "Error eliminando la planilla.", "eliminarTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta getTipoPlanillaPorCodigo(String codigo) {
        try {
            Query qry = em.createNamedQuery("Tipoplanilla.findByTplaCodigo", TipoPlanilla.class);
            qry.setParameter("codigo", codigo);
            TipoPlanilla tipoPlanilla = (TipoPlanilla) qry.getSingleResult();
            return new Respuesta(true, "", "", "TipoPlanilla", new TipoPlanillaDto(tipoPlanilla));
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe una planilla con el código ingresado.", "getTipoPlanillaPorCodigo NoResultException");
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error obteniendo la planilla por código", ex);
            return new Respuesta(false, "Error obteniendo la planilla.", "getTipoPlanillaPorCodigo " + ex.getMessage());
        }
    }

}
