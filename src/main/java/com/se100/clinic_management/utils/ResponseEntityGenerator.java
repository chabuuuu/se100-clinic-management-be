package com.se100.clinic_management.utils;

import com.se100.clinic_management.dto.base_format.RequestPageableVO;
import com.se100.clinic_management.dto.base_format.ResponsePageableVO;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.dto.base_format.ResponseVOBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseEntityGenerator {
    /*
    * Generate ResponseEntity with status CREATED 201
    * Using for any POST method
     */
    public static ResponseEntity<ResponseVO> createdFormat (Object body){
        return new ResponseEntity<>((new ResponseVOBuilder().addData(body, HttpStatus.CREATED).build()), HttpStatus.CREATED);
    }

    /*
    * Generate ResponseEntity with status OK 200
    * Using for any GET MANY method
     */
    public static ResponseEntity<ResponseVO> findManyFormat (List<Object> body) {
        return new ResponseEntity<>((new ResponseVOBuilder().addData(body).build()), HttpStatus.OK);
    }

    /*
     * Generate ResponseEntity with status OK 200
     * Using for any DELETE method
     */
    public static ResponseEntity<ResponseVO> deleteFormat (Object body) {
        return new ResponseEntity<>((new ResponseVOBuilder().addData(body).build()), HttpStatus.OK);
    }

    /*
     * Generate ResponseEntity with status OK 200
     * Using for any UPDATE method
     */
    public static ResponseEntity<ResponseVO> updateFormat (Object body) {
        return new ResponseEntity<>((new ResponseVOBuilder().addData(body).build()), HttpStatus.OK);
    }

    /*
     * Generate ResponseEntity with status OK 200
     * Using for any GET ONE method
     */
    public static ResponseEntity<ResponseVO> findOneFormat (Object body) {
        return new ResponseEntity<>((new ResponseVOBuilder().addData(body).build()), HttpStatus.OK);
    }

    /*
     * Generate ResponseEntity with status OK 200
     */
    public static ResponseEntity<ResponseVO> okFormat (Object body) {
        return new ResponseEntity<>((new ResponseVOBuilder().addData(body).build()), HttpStatus.OK);
    }

    /*
     * Generate ResponseEntity with status OK 200
     * Using for any GET ONE method
     */
    public static ResponseEntity<ResponseVO> find (Object body) {
        return new ResponseEntity<>((new ResponseVOBuilder().addData(body).build()), HttpStatus.OK);
    }

    /*
     * Generate ResponseEntity with paging
     */
    public static ResponseEntity<ResponseVO> pagingFormat (Page<?> body, RequestPageableVO request) {
        List<?> product_list = body.getContent();
        ResponsePageableVO responseVo = new ResponsePageableVO((int) body.getTotalElements(), product_list, request);
        return new ResponseEntity<>(new ResponseVOBuilder().addData(responseVo).build(), HttpStatus.OK);
    }
}
