import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("dw")
public final class class117 {
   @ObfuscatedName("p")
   static class110 field1989 = new class110();

   @ObfuscatedName("w")
   static void method2560(class110 var0) {
      byte var2 = var0.field1922;
      int var3 = var0.field1939;
      int var4 = var0.field1938;
      int var5 = var0.field1934;
      int[] var6 = class130.field2066;
      int var7 = var0.field1928;
      byte[] var8 = var0.field1918;
      int var9 = var0.field1945;
      int var10 = var0.field1920;
      int var12 = var0.field1946 + 1;

      label114:
      while(true) {
         if(var3 > 0) {
            while(true) {
               if(var10 == 0) {
                  break label114;
               }

               if(var3 == 1) {
                  if(var10 == 0) {
                     var3 = 1;
                     break label114;
                  }

                  var8[var9] = var2;
                  ++var9;
                  --var10;
                  break;
               }

               var8[var9] = var2;
               --var3;
               ++var9;
               --var10;
            }
         }

         boolean var14 = true;

         byte var1;
         while(var14) {
            var14 = false;
            if(var4 == var12) {
               var3 = 0;
               break label114;
            }

            var2 = (byte)var5;
            var7 = var6[var7];
            var1 = (byte)(var7 & 255);
            var7 >>= 8;
            ++var4;
            if(var1 != var5) {
               var5 = var1;
               if(var10 == 0) {
                  var3 = 1;
                  break label114;
               }

               var8[var9] = var2;
               ++var9;
               --var10;
               var14 = true;
            } else if(var4 == var12) {
               if(var10 == 0) {
                  var3 = 1;
                  break label114;
               }

               var8[var9] = var2;
               ++var9;
               --var10;
               var14 = true;
            }
         }

         var3 = 2;
         var7 = var6[var7];
         var1 = (byte)(var7 & 255);
         var7 >>= 8;
         ++var4;
         if(var4 != var12) {
            if(var1 != var5) {
               var5 = var1;
            } else {
               var3 = 3;
               var7 = var6[var7];
               var1 = (byte)(var7 & 255);
               var7 >>= 8;
               ++var4;
               if(var4 != var12) {
                  if(var1 != var5) {
                     var5 = var1;
                  } else {
                     var7 = var6[var7];
                     var1 = (byte)(var7 & 255);
                     var7 >>= 8;
                     ++var4;
                     var3 = (var1 & 255) + 4;
                     var7 = var6[var7];
                     var5 = (byte)(var7 & 255);
                     var7 >>= 8;
                     ++var4;
                  }
               }
            }
         }
      }

      int var13 = var0.field1921;
      var0.field1921 += var10 - var10;
      if(var0.field1921 < var13) {
         ;
      }

      var0.field1922 = var2;
      var0.field1939 = var3;
      var0.field1938 = var4;
      var0.field1934 = var5;
      class130.field2066 = var6;
      var0.field1928 = var7;
      var0.field1918 = var8;
      var0.field1945 = var9;
      var0.field1920 = var10;
   }

   @ObfuscatedName("d")
   static void method2561(class110 var0) {
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;
      boolean var8 = false;
      boolean var9 = false;
      boolean var10 = false;
      boolean var11 = false;
      boolean var12 = false;
      boolean var13 = false;
      boolean var14 = false;
      boolean var15 = false;
      boolean var16 = false;
      boolean var17 = false;
      boolean var18 = false;
      boolean var19 = false;
      boolean var20 = false;
      boolean var21 = false;
      int var22 = 0;
      int[] var23 = null;
      int[] var24 = null;
      int[] var25 = null;
      var0.field1926 = 1;
      if(class130.field2066 == null) {
         class130.field2066 = new int[var0.field1926 * 100000];
      }

      boolean var26 = true;

      while(true) {
         while(var26) {
            byte var1 = method2571(var0);
            if(var1 == 23) {
               return;
            }

            var1 = method2571(var0);
            var1 = method2571(var0);
            var1 = method2571(var0);
            var1 = method2571(var0);
            var1 = method2571(var0);
            var1 = method2571(var0);
            var1 = method2571(var0);
            var1 = method2571(var0);
            var1 = method2571(var0);
            var1 = method2563(var0);
            if(var1 != 0) {
               ;
            }

            var0.field1927 = 0;
            var1 = method2571(var0);
            var0.field1927 = var0.field1927 << 8 | var1 & 255;
            var1 = method2571(var0);
            var0.field1927 = var0.field1927 << 8 | var1 & 255;
            var1 = method2571(var0);
            var0.field1927 = var0.field1927 << 8 | var1 & 255;

            int var35;
            for(var35 = 0; var35 < 16; ++var35) {
               var1 = method2563(var0);
               if(var1 == 1) {
                  var0.field1942[var35] = true;
               } else {
                  var0.field1942[var35] = false;
               }
            }

            for(var35 = 0; var35 < 256; ++var35) {
               var0.field1935[var35] = false;
            }

            int var36;
            for(var35 = 0; var35 < 16; ++var35) {
               if(var0.field1942[var35]) {
                  for(var36 = 0; var36 < 16; ++var36) {
                     var1 = method2563(var0);
                     if(var1 == 1) {
                        var0.field1935[var35 * 16 + var36] = true;
                     }
                  }
               }
            }

            method2565(var0);
            int var38 = var0.field1933 + 2;
            int var39 = method2564(3, var0);
            int var40 = method2564(15, var0);

            for(var35 = 0; var35 < var40; ++var35) {
               var36 = 0;

               while(true) {
                  var1 = method2563(var0);
                  if(var1 == 0) {
                     var0.field1940[var35] = (byte)var36;
                     break;
                  }

                  ++var36;
               }
            }

            byte[] var27 = new byte[6];

            byte var29;
            for(var29 = 0; var29 < var39; var27[var29] = var29++) {
               ;
            }

            for(var35 = 0; var35 < var40; ++var35) {
               var29 = var0.field1940[var35];

               byte var28;
               for(var28 = var27[var29]; var29 > 0; --var29) {
                  var27[var29] = var27[var29 - 1];
               }

               var27[0] = var28;
               var0.field1931[var35] = var28;
            }

            int var37;
            for(var37 = 0; var37 < var39; ++var37) {
               int var49 = method2564(5, var0);

               for(var35 = 0; var35 < var38; ++var35) {
                  while(true) {
                     var1 = method2563(var0);
                     if(var1 == 0) {
                        var0.field1941[var37][var35] = (byte)var49;
                        break;
                     }

                     var1 = method2563(var0);
                     if(var1 == 0) {
                        ++var49;
                     } else {
                        --var49;
                     }
                  }
               }
            }

            for(var37 = 0; var37 < var39; ++var37) {
               byte var2 = 32;
               byte var3 = 0;

               for(var35 = 0; var35 < var38; ++var35) {
                  if(var0.field1941[var37][var35] > var3) {
                     var3 = var0.field1941[var37][var35];
                  }

                  if(var0.field1941[var37][var35] < var2) {
                     var2 = var0.field1941[var37][var35];
                  }
               }

               method2567(var0.field1948[var37], var0.field1943[var37], var0.field1944[var37], var0.field1941[var37], var2, var3, var38);
               var0.field1911[var37] = var2;
            }

            int var41 = var0.field1933 + 1;
            int var42 = -1;
            byte var43 = 0;

            for(var35 = 0; var35 <= 255; ++var35) {
               var0.field1930[var35] = 0;
            }

            int var56 = 4095;

            int var54;
            int var55;
            for(var54 = 15; var54 >= 0; --var54) {
               for(var55 = 15; var55 >= 0; --var55) {
                  var0.field1937[var56] = (byte)(var54 * 16 + var55);
                  --var56;
               }

               var0.field1924[var54] = var56 + 1;
            }

            int var46 = 0;
            byte var53;
            if(var43 == 0) {
               ++var42;
               var43 = 50;
               var53 = var0.field1931[var42];
               var22 = var0.field1911[var53];
               var23 = var0.field1948[var53];
               var25 = var0.field1944[var53];
               var24 = var0.field1943[var53];
            }

            int var44 = var43 - 1;
            int var50 = var22;

            int var51;
            byte var52;
            for(var51 = method2564(var22, var0); var51 > var23[var50]; var51 = var51 << 1 | var52) {
               ++var50;
               var52 = method2563(var0);
            }

            int var45 = var25[var51 - var24[var50]];

            while(true) {
               while(var45 != var41) {
                  if(var45 != 0 && var45 != 1) {
                     int var33 = var45 - 1;
                     int var30;
                     if(var33 < 16) {
                        var30 = var0.field1924[0];

                        for(var1 = var0.field1937[var30 + var33]; var33 > 3; var33 -= 4) {
                           int var34 = var30 + var33;
                           var0.field1937[var34] = var0.field1937[var34 - 1];
                           var0.field1937[var34 - 1] = var0.field1937[var34 - 2];
                           var0.field1937[var34 - 2] = var0.field1937[var34 - 3];
                           var0.field1937[var34 - 3] = var0.field1937[var34 - 4];
                        }

                        while(var33 > 0) {
                           var0.field1937[var30 + var33] = var0.field1937[var30 + var33 - 1];
                           --var33;
                        }

                        var0.field1937[var30] = var1;
                     } else {
                        int var31 = var33 / 16;
                        int var32 = var33 % 16;
                        var30 = var0.field1924[var31] + var32;

                        for(var1 = var0.field1937[var30]; var30 > var0.field1924[var31]; --var30) {
                           var0.field1937[var30] = var0.field1937[var30 - 1];
                        }

                        ++var0.field1924[var31];

                        while(var31 > 0) {
                           --var0.field1924[var31];
                           var0.field1937[var0.field1924[var31]] = var0.field1937[var0.field1924[var31 - 1] + 16 - 1];
                           --var31;
                        }

                        --var0.field1924[0];
                        var0.field1937[var0.field1924[0]] = var1;
                        if(var0.field1924[0] == 0) {
                           var56 = 4095;

                           for(var54 = 15; var54 >= 0; --var54) {
                              for(var55 = 15; var55 >= 0; --var55) {
                                 var0.field1937[var56] = var0.field1937[var0.field1924[var54] + var55];
                                 --var56;
                              }

                              var0.field1924[var54] = var56 + 1;
                           }
                        }
                     }

                     ++var0.field1930[var0.field1936[var1 & 255] & 255];
                     class130.field2066[var46] = var0.field1936[var1 & 255] & 255;
                     ++var46;
                     if(var44 == 0) {
                        ++var42;
                        var44 = 50;
                        var53 = var0.field1931[var42];
                        var22 = var0.field1911[var53];
                        var23 = var0.field1948[var53];
                        var25 = var0.field1944[var53];
                        var24 = var0.field1943[var53];
                     }

                     --var44;
                     var50 = var22;

                     for(var51 = method2564(var22, var0); var51 > var23[var50]; var51 = var51 << 1 | var52) {
                        ++var50;
                        var52 = method2563(var0);
                     }

                     var45 = var25[var51 - var24[var50]];
                  } else {
                     int var47 = -1;
                     int var48 = 1;

                     do {
                        if(var45 == 0) {
                           var47 += var48;
                        } else if(var45 == 1) {
                           var47 += 2 * var48;
                        }

                        var48 *= 2;
                        if(var44 == 0) {
                           ++var42;
                           var44 = 50;
                           var53 = var0.field1931[var42];
                           var22 = var0.field1911[var53];
                           var23 = var0.field1948[var53];
                           var25 = var0.field1944[var53];
                           var24 = var0.field1943[var53];
                        }

                        --var44;
                        var50 = var22;

                        for(var51 = method2564(var22, var0); var51 > var23[var50]; var51 = var51 << 1 | var52) {
                           ++var50;
                           var52 = method2563(var0);
                        }

                        var45 = var25[var51 - var24[var50]];
                     } while(var45 == 0 || var45 == 1);

                     ++var47;
                     var1 = var0.field1936[var0.field1937[var0.field1924[0]] & 255];

                     for(var0.field1930[var1 & 255] += var47; var47 > 0; --var47) {
                        class130.field2066[var46] = var1 & 255;
                        ++var46;
                     }
                  }
               }

               var0.field1939 = 0;
               var0.field1922 = 0;
               var0.field1932[0] = 0;

               for(var35 = 1; var35 <= 256; ++var35) {
                  var0.field1932[var35] = var0.field1930[var35 - 1];
               }

               for(var35 = 1; var35 <= 256; ++var35) {
                  var0.field1932[var35] += var0.field1932[var35 - 1];
               }

               for(var35 = 0; var35 < var46; ++var35) {
                  var1 = (byte)(class130.field2066[var35] & 255);
                  class130.field2066[var0.field1932[var1 & 255]] |= var35 << 8;
                  ++var0.field1932[var1 & 255];
               }

               var0.field1928 = class130.field2066[var0.field1927] >> 8;
               var0.field1938 = 0;
               var0.field1928 = class130.field2066[var0.field1928];
               var0.field1934 = (byte)(var0.field1928 & 255);
               var0.field1928 >>= 8;
               ++var0.field1938;
               var0.field1946 = var46;
               method2560(var0);
               if(var0.field1938 == var0.field1946 + 1 && var0.field1939 == 0) {
                  var26 = true;
                  break;
               }

               var26 = false;
               break;
            }
         }

         return;
      }
   }

   @ObfuscatedName("y")
   static byte method2563(class110 var0) {
      return (byte)method2564(1, var0);
   }

   @ObfuscatedName("k")
   static int method2564(int var0, class110 var1) {
      while(var1.field1925 < var0) {
         var1.field1916 = var1.field1916 << 8 | var1.field1915[var1.field1923] & 255;
         var1.field1925 += 8;
         ++var1.field1923;
         ++var1.field1917;
         if(var1.field1917 == 0) {
            ;
         }
      }

      int var3 = var1.field1916 >> var1.field1925 - var0 & (1 << var0) - 1;
      var1.field1925 -= var0;
      return var3;
   }

   @ObfuscatedName("r")
   static void method2565(class110 var0) {
      var0.field1933 = 0;

      for(int var1 = 0; var1 < 256; ++var1) {
         if(var0.field1935[var1]) {
            var0.field1936[var0.field1933] = (byte)var1;
            ++var0.field1933;
         }
      }

   }

   @ObfuscatedName("p")
   static void method2567(int[] var0, int[] var1, int[] var2, byte[] var3, int var4, int var5, int var6) {
      int var7 = 0;

      int var8;
      for(var8 = var4; var8 <= var5; ++var8) {
         for(int var9 = 0; var9 < var6; ++var9) {
            if(var3[var9] == var8) {
               var2[var7] = var9;
               ++var7;
            }
         }
      }

      for(var8 = 0; var8 < 23; ++var8) {
         var1[var8] = 0;
      }

      for(var8 = 0; var8 < var6; ++var8) {
         ++var1[var3[var8] + 1];
      }

      for(var8 = 1; var8 < 23; ++var8) {
         var1[var8] += var1[var8 - 1];
      }

      for(var8 = 0; var8 < 23; ++var8) {
         var0[var8] = 0;
      }

      int var10 = 0;

      for(var8 = var4; var8 <= var5; ++var8) {
         var10 += var1[var8 + 1] - var1[var8];
         var0[var8] = var10 - 1;
         var10 <<= 1;
      }

      for(var8 = var4 + 1; var8 <= var5; ++var8) {
         var1[var8] = (var0[var8 - 1] + 1 << 1) - var1[var8];
      }

   }

   @ObfuscatedName("c")
   static byte method2571(class110 var0) {
      return (byte)method2564(8, var0);
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "([BI[BII)I",
      garbageValue = "9"
   )
   public static int method2578(byte[] var0, int var1, byte[] var2, int var3, int var4) {
      class110 var5 = field1989;
      synchronized(field1989) {
         field1989.field1915 = var2;
         field1989.field1923 = var4;
         field1989.field1918 = var0;
         field1989.field1945 = 0;
         field1989.field1920 = var1;
         field1989.field1925 = 0;
         field1989.field1916 = 0;
         field1989.field1917 = 0;
         field1989.field1921 = 0;
         method2561(field1989);
         var1 -= field1989.field1920;
         field1989.field1915 = null;
         field1989.field1918 = null;
         return var1;
      }
   }
}
