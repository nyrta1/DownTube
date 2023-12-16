import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable({
  providedIn: 'root'
})
export class CryptoService {
  constructor() { }

  encrypt(value: string): string {
    const encrypted = CryptoJS.SHA256(value).toString();
    return encrypted;
  }
}