package com.distribuidora.despacho.repository;

import com.distribuidora.despacho.entity.Despacho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DespachoRepository extends JpaRepository<Despacho, UUID> {
}
