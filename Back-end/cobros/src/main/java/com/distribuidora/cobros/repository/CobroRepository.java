package com.distribuidora.cobros.repository;

import com.distribuidora.cobros.entity.Cobro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CobroRepository extends JpaRepository<Cobro, UUID> {
}