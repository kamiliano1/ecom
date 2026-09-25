package com.demo.consumer.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor // Lombok - generuje konstruktor z polami final, Spring wstrzykuje przez niego zależności
public class RestClientProvider {

    // Gotowy klient HTTP zdefiniowany w RestClientConfig - bez wiedzy o konkretnym adresie usługi
    private final RestClient restClient;

    // Klient odpowiedzialny za wybór jednej z zarejestrowanych w Eurece instancji usługi
    // (odpowiednik tego, co normalnie robiłby @LoadBalanced automatycznie "pod spodem")
    private final LoadBalancerClient loadBalancerClient;

    public String getInstanceInfo() {
        // Pytamy load balancer: "spośród wszystkich żyjących instancji usługi o nazwie 'provider',
        // wybierz jedną" - domyślnie działa to na zasadzie round-robin
        ServiceInstance instance = loadBalancerClient.choose("provider");

        // Zabezpieczenie: jeśli żadna instancja 'provider' nie jest aktualnie zarejestrowana
        // w Eurece (np. usługa nie wystartowała albo jeszcze się nie zdążyła zarejestrować),
        // choose() zwróci null - bez tego sprawdzenia dostalibyśmy NullPointerException
        if (instance == null) {
            throw new IllegalStateException("Brak dostępnych instancji usługi 'provider'");
        }

        // instance.getUri() zwraca pełny fizyczny adres wybranej instancji,
        // np. "http://Kamil:8081" - to właśnie ten kawałek zastępuje mechanizm
        // @LoadBalanced, który normalnie podmieniłby "http://provider" na taki adres sam
        String url = instance.getUri() + "/instance-info";

        // Wykonujemy właściwe zapytanie GET pod skonkretyzowany, pełny adres URL
        return restClient.get()
                .uri(url)
                .retrieve()   // wysyła request i przygotowuje odpowiedź do odczytu
                .body(String.class); // deserializuje ciało odpowiedzi do Stringa
    }
}
