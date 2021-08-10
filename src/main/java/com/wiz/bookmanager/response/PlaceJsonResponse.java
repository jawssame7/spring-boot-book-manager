package com.wiz.bookmanager.response;

import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Map;


@Data
public class PlaceJsonResponse extends JsonResponse {

    /**
     * id
     */
    private Long id;

    /**
     * 名前
     */
    private String name;

}
