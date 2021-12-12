ptables -t nat -D PREROUTING 1
iptables -t nat -D PREROUTING 1


certbot certificates
certbot renew


iptables -A PREROUTING -t nat -i eth0 -p tcp --dport 80 -j REDIRECT --to-port 8080
iptables -A PREROUTING -t nat -i eth0 -p tcp --dport 443 -j REDIRECT --to-port 8443



