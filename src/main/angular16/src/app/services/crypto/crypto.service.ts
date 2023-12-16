import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable({
  providedIn: 'root'
})
export class CryptoService {
  private readonly SECRET_KEY_256_BIT_WEP: string = "462CBD3D2172B4FC858DAB3D3C998";

  constructor() { }

  encrypt(value: string): string {
    const encrypted = CryptoJS.AES.encrypt(value, this.SECRET_KEY_256_BIT_WEP).toString();
    return encrypted;
  }
}