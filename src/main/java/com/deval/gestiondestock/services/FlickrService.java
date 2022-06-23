package com.deval.gestiondestock.services;

import com.flickr4java.flickr.FlickrException;

import java.io.InputStream;

public interface FlickrService {
    String savedPhoto(InputStream photo, String title) throws FlickrException;
}
