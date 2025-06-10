package com.gestortallermecanico.http;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.gestortallermecanico.model.Factura;
import com.gestortallermecanico.repository.FacturaRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@WebServlet("/create-checkout-session")
public class Server extends HttpServlet{

    @Value("${stripe.key.secret}")
    private String stripeSecretKey;

    @Autowired
    private FacturaRepository repoFact;

    @Override
    @PostConstruct
    public void init() throws ServletException {
        super.init();
        Stripe.apiKey = stripeSecretKey;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();

        try {
            JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);
            String facturaIdStr = json.get("facturaId").getAsString();
            Long facturaId = Long.parseLong(facturaIdStr);

            Factura factura = repoFact.findWithClienteAndLineasById(facturaId).get();
            if (factura == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().print("{\"error\": \"Factura no encontrada\"}");
                return;
            }
            long amount = (long)(factura.getTotalFactura() * 100);

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                    .setReturnUrl("http://localhost:8080/return.html?facturaId=" + facturaId + "&session_id={CHECKOUT_SESSION_ID}")

                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount(amount)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Factura #" + factura.getNumeroFact())
                                                                    .setDescription("Pago correspondiente a la factura del cliente")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            // Después de crear la sesión:
            Session session = Session.create(params);

            // Preparamos la respuesta con sessionId
            Map<String, String> map = new HashMap<>();
            map.put("sessionId", session.getId());   //  aquí usamos getId()

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(map));
            out.flush();


        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}