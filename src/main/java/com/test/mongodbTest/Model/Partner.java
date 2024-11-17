package com.test.mongodbTest.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Partner {

    @Id
    private Integer id; // Corresponds to "_id" in MongoDB
    private String name;
    private String details;
    private Integer age;

    // While Deserializing the DATA in Payload from client side we don't accepting this value.
    // So, It will come in the pyload as null even someone try to add in the request payload.
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String updatedOn;
}


/**
 *
 * Added some changes ::
 *
 * @JsonProperty(value = "student_id ")
 * String id
 *
 * ---> Payload -> FE(student_id) Payload
 *                                  --> BE (id) Mapped
 * --> Response -> FE (student_id)
 * ============================================
 *
 * While mapping
 * READ_ONLY --- FE (data present) --> BE NULL :(   Present in Seralization --> FE (field there)
 * WRITE_ONLY -- BE (data present) --> BE Mapped :) REmove from Serializtion --> FE (NO field)
 *
 * ===================
 * ===================
 * @JsonIgnore
 * @jsonIgnoreProperties
 *
 * Deserialize and serlizliation
 *
 * No Data can come to go in Reponse
 * Payload : coming as NULL !
 * Response - No field present!
 *
 * ===================
 * ===================
 *
 * Class Level ::
 *
 * @JsonInclude(value = Include.NOT_EMPTY)
 * No NULL field will send back to in Response.!!
 *
 * ===================
 * ==================
 * @JsonAlias({"data", "date"})
 * // Allows both "data" and "date" in JSON
 *     private Date date;
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */