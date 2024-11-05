package com.test.mongodbTest.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Partner {

    @Id
    private Integer id; // Corresponds to "_id" in MongoDB
    private String name;
    private String details;
    private Integer age;
    private String updatedOn;
}


/**
 * JsonProperty(value = "student_id ")
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
 * ** JsonIgnore & jsonIgnoreProperties
 *
 * Deserialize and serlizliation
 * No Data can come ot go in Reponse
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