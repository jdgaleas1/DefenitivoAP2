package com.distribuidora.ordenes.repository;

import com.distribuidora.ordenes.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdenRepository extends JpaRepository<Orden, UUID> {
}

