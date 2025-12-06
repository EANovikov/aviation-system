DOCKER_COMPOSE = docker-compose
INFRA_SERVICES ?= aviation-api prometheus grafana tempo loki

.PHONY: all up start stop clean logs rebuild infra infra-logs infra-stop

all: up build-artifacts start

up:
	$(DOCKER_COMPOSE) up
	@echo "Waiting..."
	@$(WAIT_CMD)
	@echo "Ready!"

build-artifacts:
	@$(DOCKER_COMPOSE) build aviation-api --no-cache

start:
	$(DOCKER_COMPOSE) up -d

stop:
	$(DOCKER_COMPOSE) down

clean: stop
	$(DOCKER_COMPOSE) rm -f
	docker volume rm $$(docker volume ls -qf dangling=true) 2>/dev/null || true
	rm -rf ./aviation-api/build

logs:
	$(DOCKER_COMPOSE) logs -f --tail=200

infra:
	@echo "Starting infrastructure services: $(INFRA_SERVICES)"
	$(DOCKER_COMPOSE) up -d $(INFRA_SERVICES)
	@echo "Waiting for infrastructure...";
	@$(WAIT_CMD)
	@echo "Infrastructure is ready."

infra-logs:
	$(DOCKER_COMPOSE) logs -f --tail=200 $(INFRA_SERVICES)

infra-stop:
	$(DOCKER_COMPOSE) stop $(INFRA_SERVICES)

rebuild: clean all