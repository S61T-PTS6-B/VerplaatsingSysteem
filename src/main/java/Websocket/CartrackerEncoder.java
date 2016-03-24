/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Websocket;

import java.io.IOException;
import java.io.Writer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import proftaak.Model.CarTrackerDAO;

/**
 *
 * @author Casvan
 */
class CartrackerEncoder implements Encoder.TextStream<CarTrackerDAO> {

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void encode(CarTrackerDAO object, Writer writer) throws EncodeException, IOException {
        System.out.print("Encoder");
        JsonObject json = Json.createObjectBuilder()
                .add("message", "succeed")
                .build();
        try {
            JsonWriter jsonWriter = Json.createWriter(writer);
            jsonWriter.write(json);
        } catch (Exception e) {

        }
    }
}
