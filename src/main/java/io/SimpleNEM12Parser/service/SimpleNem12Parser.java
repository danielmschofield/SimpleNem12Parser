package io.SimpleNEM12Parser.service;

import io.SimpleNEM12Parser.entity.MeterRead;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;

@Service
public interface SimpleNem12Parser {

  /**
   * Parses Simple NEM12 file.
   * 
   * @param simpleNem12File file in Simple NEM12 format
   * @return Collection of <code>MeterRead</code> that represents the data in the given file.
   */
  Collection<MeterRead> parseSimpleNem12(File simpleNem12File);

}
