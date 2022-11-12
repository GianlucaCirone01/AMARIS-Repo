package com.amaris.it.paypal.transaction.repository;

import com.amaris.it.paypal.transaction.entity.TransazioneDto;

import org.springframework.data.repository.CrudRepository;

public interface TransazioneRepository extends CrudRepository<TransazioneDto, Integer> {


}
