package com.yuale01.mis.resources;

import java.io.File;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.multipart.FormDataParam;
import com.yuale01.mis.controller.ChildrenController;
import com.yuale01.mis.controller.DAOFactory;
import com.yuale01.mis.dao.IChildDAO;
import com.yuale01.mis.exception.CommonException;
import com.yuale01.mis.exception.ErrorMessage;
import com.yuale01.mis.po.Child;
import com.yuale01.mis.utils.Constants;

@Path("Children")
public class ChildrenResource {
    private IChildDAO childDAO = DAOFactory.getChildDAO();
    private ChildrenController controller = new ChildrenController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChildren() {
        Response res = null;
        try {
            res = Response.ok(childDAO.getChildren()).build();
        }
        catch (CommonException e) {
            ErrorMessage em = new ErrorMessage(e.getStatusCode(),
                            e.getErrorCode(), e.getMessage());
            res = Response.status(e.getStatusCode()).entity(em).build();
        }
        return res;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createChild(Child child) {
        Response res = null;
        
//          for (int i=0; i< 200; i++) { try { res =
//          Response.status(Status.CREATED
//          ).entity(childDAO.createChild(child)).build(); } catch
//          (CommonException e) { ErrorMessage em = new
//          ErrorMessage(e.getStatusCode(), e.getErrorCode(), e.getMessage());
//          res = Response.status(e.getStatusCode()).entity(em).build(); } }
         
        try {
            res = Response.status(Status.CREATED)
                            .entity(childDAO.createChild(child)).build();
        }
        catch (CommonException e) {
            ErrorMessage em = new ErrorMessage(e.getStatusCode(),
                            e.getErrorCode(), e.getMessage());
            res = Response.status(e.getStatusCode()).entity(em).build();
        }
        return res;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteChildren(Long[] ids) {
        Response res = null;
        try {
            childDAO.deleteChildren(ids);
            res = Response.ok("{\"success\": true}").build();
        }
        catch (CommonException e) {
            ErrorMessage em = new ErrorMessage(e.getStatusCode(),
                            e.getErrorCode(), e.getMessage());
            res = Response.status(e.getStatusCode()).entity(em).build();
        }
        return res;
    }

    @GET
    @Path("/{childID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChild(@PathParam("childID") Long childID) {
        Response res = null;
        try {
            res = Response.ok(childDAO.getChild(childID)).build();
        }
        catch (CommonException e) {
            ErrorMessage em = new ErrorMessage(e.getStatusCode(),
                            e.getErrorCode(), e.getMessage());
            res = Response.status(e.getStatusCode()).entity(em).build();
        }
        return res;
    }

    @PUT
    @Path("/{childID}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateChild(Child child, @PathParam("childID") Long childID) {
        Response res = null;
        try {
            childDAO.updateChild(child, childID);
            res = Response.ok("{\"success\": true}").build();// need return
                                                             // modified child
                                                             // body.
        }
        catch (CommonException e) {
            ErrorMessage em = new ErrorMessage(e.getStatusCode(),
                            e.getErrorCode(), e.getMessage());
            res = Response.status(e.getStatusCode()).entity(em).build();
        }
        return res;
    }

    @DELETE
    @Path("/{childID}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteChild(@PathParam("childID") Long childID) {
        Response res = null;
        try {
            childDAO.deleteChild(childID);
            res = Response.ok("{\"success\": true}").build();// need return
                                                             // modified child
                                                             // body.
        }
        catch (CommonException e) {
            ErrorMessage em = new ErrorMessage(e.getStatusCode(),
                            e.getErrorCode(), e.getMessage());
            res = Response.status(e.getStatusCode()).entity(em).build();
        }
        return res;
    }

    @GET
    @Path("/export")
    @Produces(Constants.MEDIA_TYPE_EXCEL)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response exportChildren() {
        Response res = null;
        try {
        	File file = controller.exportChildren("zh_CN");
        	res = Response.status(Status.OK).entity(file).
            		header("Content-Disposition", "attachment; filename=Children.xls").build();
        	file.deleteOnExit();
        }
        catch (CommonException e) {
            ErrorMessage em = new ErrorMessage(e.getStatusCode(),
                            e.getErrorCode(), e.getMessage());
            res = Response.status(e.getStatusCode()).entity(em).build();
        }
        return res;
    }
    
    @POST
    @Path("/import")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importChildren(@FormDataParam("file") InputStream fileInputStream) {
        Response res = null;
        /*try {
        	res = Response.status(Status.OK).entity(controller.exportChildren("zh_CN")).
            		header("Content-Disposition", "attachment; filename=Children.xls").build();
        }
        catch (CommonException e) {
            ErrorMessage em = new ErrorMessage(e.getStatusCode(),
                            e.getErrorCode(), e.getMessage());
            res = Response.status(e.getStatusCode()).entity(em).build();
        }*/
        return res;
    }

}
