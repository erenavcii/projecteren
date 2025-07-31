



Bu proje Kubernetes ortamına taşınmıştır.  

Gerekli Secret ve ConfigMap dosyaları `k8s/` klasöründe yer almaktadır.  

Local image kullanımı için Minikube ortamında `imagePullPolicy: IfNotPresent` ile build işlemi yapılmalıdır.  

Deployment, Secret ve ConfigMap üzerinden ortam değişkenlerini okumaktadır.



<!-- workflow trigger -->
