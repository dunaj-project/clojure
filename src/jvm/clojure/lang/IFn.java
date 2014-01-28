/**
 *   Copyright (c) Rich Hickey. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 * 	 the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

/* rich Mar 25, 2006 3:54:03 PM */

package clojure.lang;

import java.util.concurrent.Callable;

/**
 * <p><code>IFn</code> provides complete access to invoking
 * any of Clojure's <a href="http://clojure.github.io/clojure/">API</a>s.
 * You can also access any other library written in Clojure, after adding
 * either its source or compiled form to the classpath.</p>
 */
public interface IFn extends Callable, Runnable{

public Object invoke() ;

public Object invoke(Object arg1) ;

public Object invoke(Object arg1, Object arg2) ;

public Object invoke(Object arg1, Object arg2, Object arg3) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7)
		;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9, Object arg10) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9, Object arg10, Object arg11) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14)
		;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                     Object arg15) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                     Object arg15, Object arg16) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                     Object arg15, Object arg16, Object arg17) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                     Object arg15, Object arg16, Object arg17, Object arg18) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                     Object arg15, Object arg16, Object arg17, Object arg18, Object arg19) ;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                     Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20)
		;

public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                     Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                     Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20,
                     Object... args)
		;

public Object applyTo(ISeq arglist) ;

static public interface B{byte invokePrim();}
static public interface S{short invokePrim();}
static public interface I{int invokePrim();}
static public interface F{float invokePrim();}
static public interface X{boolean invokePrim();}
static public interface C{char invokePrim();}

static public interface BB{byte invokePrim(byte arg0);}
static public interface SB{byte invokePrim(short arg0);}
static public interface IB{byte invokePrim(int arg0);}
static public interface FB{byte invokePrim(float arg0);}
static public interface XB{byte invokePrim(boolean arg0);}
static public interface CB{byte invokePrim(char arg0);}
static public interface LB{byte invokePrim(long arg0);}
static public interface DB{byte invokePrim(double arg0);}
static public interface OB{byte invokePrim(Object arg0);}

static public interface BS{short invokePrim(byte arg0);}
static public interface SS{short invokePrim(short arg0);}
static public interface IS{short invokePrim(int arg0);}
static public interface FS{short invokePrim(float arg0);}
static public interface XS{short invokePrim(boolean arg0);}
static public interface CS{short invokePrim(char arg0);}
static public interface LS{short invokePrim(long arg0);}
static public interface DS{short invokePrim(double arg0);}
static public interface OS{short invokePrim(Object arg0);}

static public interface BI{int invokePrim(byte arg0);}
static public interface SI{int invokePrim(short arg0);}
static public interface II{int invokePrim(int arg0);}
static public interface FI{int invokePrim(float arg0);}
static public interface XI{int invokePrim(boolean arg0);}
static public interface CI{int invokePrim(char arg0);}
static public interface LI{int invokePrim(long arg0);}
static public interface DI{int invokePrim(double arg0);}
static public interface OI{int invokePrim(Object arg0);}

static public interface BF{float invokePrim(byte arg0);}
static public interface SF{float invokePrim(short arg0);}
static public interface IF{float invokePrim(int arg0);}
static public interface FF{float invokePrim(float arg0);}
static public interface XF{float invokePrim(boolean arg0);}
static public interface CF{float invokePrim(char arg0);}
static public interface LF{float invokePrim(long arg0);}
static public interface DF{float invokePrim(double arg0);}
static public interface OF{float invokePrim(Object arg0);}

static public interface BX{boolean invokePrim(byte arg0);}
static public interface SX{boolean invokePrim(short arg0);}
static public interface IX{boolean invokePrim(int arg0);}
static public interface FX{boolean invokePrim(float arg0);}
static public interface XX{boolean invokePrim(boolean arg0);}
static public interface CX{boolean invokePrim(char arg0);}
static public interface LX{boolean invokePrim(long arg0);}
static public interface DX{boolean invokePrim(double arg0);}
static public interface OX{boolean invokePrim(Object arg0);}

static public interface BC{char invokePrim(byte arg0);}
static public interface SC{char invokePrim(short arg0);}
static public interface IC{char invokePrim(int arg0);}
static public interface FC{char invokePrim(float arg0);}
static public interface XC{char invokePrim(boolean arg0);}
static public interface CC{char invokePrim(char arg0);}
static public interface LC{char invokePrim(long arg0);}
static public interface DC{char invokePrim(double arg0);}
static public interface OC{char invokePrim(Object arg0);}

static public interface BL{long invokePrim(byte arg0);}
static public interface SL{long invokePrim(short arg0);}
static public interface IL{long invokePrim(int arg0);}
static public interface FL{long invokePrim(float arg0);}
static public interface XL{long invokePrim(boolean arg0);}
static public interface CL{long invokePrim(char arg0);}

static public interface BD{double invokePrim(byte arg0);}
static public interface SD{double invokePrim(short arg0);}
static public interface ID{double invokePrim(int arg0);}
static public interface FD{double invokePrim(float arg0);}
static public interface XD{double invokePrim(boolean arg0);}
static public interface CD{double invokePrim(char arg0);}

static public interface BO{Object invokePrim(byte arg0);}
static public interface SO{Object invokePrim(short arg0);}
static public interface IO{Object invokePrim(int arg0);}
static public interface FO{Object invokePrim(float arg0);}
static public interface XO{Object invokePrim(boolean arg0);}
static public interface CO{Object invokePrim(char arg0);}

static public interface BBB{byte invokePrim(byte arg0, byte arg1);}
static public interface SSB{byte invokePrim(short arg0, short arg1);}
static public interface IIB{byte invokePrim(int arg0, int arg1);}
static public interface FFB{byte invokePrim(float arg0, float arg1);}
static public interface XXB{byte invokePrim(boolean arg0, boolean arg1);}
static public interface CCB{byte invokePrim(char arg0, char arg1);}
static public interface LLB{byte invokePrim(long arg0, long arg1);}
static public interface DDB{byte invokePrim(double arg0, double arg1);}
static public interface OOB{byte invokePrim(Object arg0, Object arg1);}

static public interface BBS{short invokePrim(byte arg0, byte arg1);}
static public interface SSS{short invokePrim(short arg0, short arg1);}
static public interface IIS{short invokePrim(int arg0, int arg1);}
static public interface FFS{short invokePrim(float arg0, float arg1);}
static public interface XXS{short invokePrim(boolean arg0, boolean arg1);}
static public interface CCS{short invokePrim(char arg0, char arg1);}
static public interface LLS{short invokePrim(long arg0, long arg1);}
static public interface DDS{short invokePrim(double arg0, double arg1);}
static public interface OOS{short invokePrim(Object arg0, Object arg1);}

static public interface BBI{int invokePrim(byte arg0, byte arg1);}
static public interface SSI{int invokePrim(short arg0, short arg1);}
static public interface III{int invokePrim(int arg0, int arg1);}
static public interface FFI{int invokePrim(float arg0, float arg1);}
static public interface XXI{int invokePrim(boolean arg0, boolean arg1);}
static public interface CCI{int invokePrim(char arg0, char arg1);}
static public interface LLI{int invokePrim(long arg0, long arg1);}
static public interface DDI{int invokePrim(double arg0, double arg1);}
static public interface OOI{int invokePrim(Object arg0, Object arg1);}

static public interface BBF{float invokePrim(byte arg0, byte arg1);}
static public interface SSF{float invokePrim(short arg0, short arg1);}
static public interface IIF{float invokePrim(int arg0, int arg1);}
static public interface FFF{float invokePrim(float arg0, float arg1);}
static public interface XXF{float invokePrim(boolean arg0, boolean arg1);}
static public interface CCF{float invokePrim(char arg0, char arg1);}
static public interface LLF{float invokePrim(long arg0, long arg1);}
static public interface DDF{float invokePrim(double arg0, double arg1);}
static public interface OOF{float invokePrim(Object arg0, Object arg1);}

static public interface BBX{boolean invokePrim(byte arg0, byte arg1);}
static public interface SSX{boolean invokePrim(short arg0, short arg1);}
static public interface IIX{boolean invokePrim(int arg0, int arg1);}
static public interface FFX{boolean invokePrim(float arg0, float arg1);}
static public interface XXX{boolean invokePrim(boolean arg0, boolean arg1);}
static public interface CCX{boolean invokePrim(char arg0, char arg1);}
static public interface LLX{boolean invokePrim(long arg0, long arg1);}
static public interface DDX{boolean invokePrim(double arg0, double arg1);}
static public interface OOX{boolean invokePrim(Object arg0, Object arg1);}

static public interface BBC{char invokePrim(byte arg0, byte arg1);}
static public interface SSC{char invokePrim(short arg0, short arg1);}
static public interface IIC{char invokePrim(int arg0, int arg1);}
static public interface FFC{char invokePrim(float arg0, float arg1);}
static public interface XXC{char invokePrim(boolean arg0, boolean arg1);}
static public interface CCC{char invokePrim(char arg0, char arg1);}
static public interface LLC{char invokePrim(long arg0, long arg1);}
static public interface DDC{char invokePrim(double arg0, double arg1);}
static public interface OOC{char invokePrim(Object arg0, Object arg1);}

static public interface BBL{long invokePrim(byte arg0, byte arg1);}
static public interface SSL{long invokePrim(short arg0, short arg1);}
static public interface IIL{long invokePrim(int arg0, int arg1);}
static public interface FFL{long invokePrim(float arg0, float arg1);}
static public interface XXL{long invokePrim(boolean arg0, boolean arg1);}
static public interface CCL{long invokePrim(char arg0, char arg1);}

static public interface BBD{double invokePrim(byte arg0, byte arg1);}
static public interface SSD{double invokePrim(short arg0, short arg1);}
static public interface IID{double invokePrim(int arg0, int arg1);}
static public interface FFD{double invokePrim(float arg0, float arg1);}
static public interface XXD{double invokePrim(boolean arg0, boolean arg1);}
static public interface CCD{double invokePrim(char arg0, char arg1);}

static public interface BBO{Object invokePrim(byte arg0, byte arg1);}
static public interface SSO{Object invokePrim(short arg0, short arg1);}
static public interface IIO{Object invokePrim(int arg0, int arg1);}
static public interface FFO{Object invokePrim(float arg0, float arg1);}
static public interface XXO{Object invokePrim(boolean arg0, boolean arg1);}
static public interface CCO{Object invokePrim(char arg0, char arg1);}

static public interface OBB{byte invokePrim(Object arg0, byte arg1);}
static public interface OSB{byte invokePrim(Object arg0, short arg1);}
static public interface OIB{byte invokePrim(Object arg0, int arg1);}
static public interface OFB{byte invokePrim(Object arg0, float arg1);}
static public interface OXB{byte invokePrim(Object arg0, boolean arg1);}
static public interface OCB{byte invokePrim(Object arg0, char arg1);}
static public interface OLB{byte invokePrim(Object arg0, long arg1);}
static public interface ODB{byte invokePrim(Object arg0, double arg1);}

static public interface OBS{short invokePrim(Object arg0, byte arg1);}
static public interface OSS{short invokePrim(Object arg0, short arg1);}
static public interface OIS{short invokePrim(Object arg0, int arg1);}
static public interface OFS{short invokePrim(Object arg0, float arg1);}
static public interface OXS{short invokePrim(Object arg0, boolean arg1);}
static public interface OCS{short invokePrim(Object arg0, char arg1);}
static public interface OLS{short invokePrim(Object arg0, long arg1);}
static public interface ODS{short invokePrim(Object arg0, double arg1);}

static public interface OBI{int invokePrim(Object arg0, byte arg1);}
static public interface OSI{int invokePrim(Object arg0, short arg1);}
static public interface OII{int invokePrim(Object arg0, int arg1);}
static public interface OFI{int invokePrim(Object arg0, float arg1);}
static public interface OXI{int invokePrim(Object arg0, boolean arg1);}
static public interface OCI{int invokePrim(Object arg0, char arg1);}
static public interface OLI{int invokePrim(Object arg0, long arg1);}
static public interface ODI{int invokePrim(Object arg0, double arg1);}

static public interface OBF{float invokePrim(Object arg0, byte arg1);}
static public interface OSF{float invokePrim(Object arg0, short arg1);}
static public interface OIF{float invokePrim(Object arg0, int arg1);}
static public interface OFF{float invokePrim(Object arg0, float arg1);}
static public interface OXF{float invokePrim(Object arg0, boolean arg1);}
static public interface OCF{float invokePrim(Object arg0, char arg1);}
static public interface OLF{float invokePrim(Object arg0, long arg1);}
static public interface ODF{float invokePrim(Object arg0, double arg1);}

static public interface OBX{boolean invokePrim(Object arg0, byte arg1);}
static public interface OSX{boolean invokePrim(Object arg0, short arg1);}
static public interface OIX{boolean invokePrim(Object arg0, int arg1);}
static public interface OFX{boolean invokePrim(Object arg0, float arg1);}
static public interface OXX{boolean invokePrim(Object arg0, boolean arg1);}
static public interface OCX{boolean invokePrim(Object arg0, char arg1);}
static public interface OLX{boolean invokePrim(Object arg0, long arg1);}
static public interface ODX{boolean invokePrim(Object arg0, double arg1);}

static public interface OBC{char invokePrim(Object arg0, byte arg1);}
static public interface OSC{char invokePrim(Object arg0, short arg1);}
static public interface OIC{char invokePrim(Object arg0, int arg1);}
static public interface OFC{char invokePrim(Object arg0, float arg1);}
static public interface OXC{char invokePrim(Object arg0, boolean arg1);}
static public interface OCC{char invokePrim(Object arg0, char arg1);}
static public interface OLC{char invokePrim(Object arg0, long arg1);}
static public interface ODC{char invokePrim(Object arg0, double arg1);}

static public interface OBL{long invokePrim(Object arg0, byte arg1);}
static public interface OSL{long invokePrim(Object arg0, short arg1);}
static public interface OIL{long invokePrim(Object arg0, int arg1);}
static public interface OFL{long invokePrim(Object arg0, float arg1);}
static public interface OXL{long invokePrim(Object arg0, boolean arg1);}
static public interface OCL{long invokePrim(Object arg0, char arg1);}

static public interface OBD{double invokePrim(Object arg0, byte arg1);}
static public interface OSD{double invokePrim(Object arg0, short arg1);}
static public interface OID{double invokePrim(Object arg0, int arg1);}
static public interface OFD{double invokePrim(Object arg0, float arg1);}
static public interface OXD{double invokePrim(Object arg0, boolean arg1);}
static public interface OCD{double invokePrim(Object arg0, char arg1);}

static public interface OBO{Object invokePrim(Object arg0, byte arg1);}
static public interface OSO{Object invokePrim(Object arg0, short arg1);}
static public interface OIO{Object invokePrim(Object arg0, int arg1);}
static public interface OFO{Object invokePrim(Object arg0, float arg1);}
static public interface OXO{Object invokePrim(Object arg0, boolean arg1);}
static public interface OCO{Object invokePrim(Object arg0, char arg1);}

static public interface OBBB{byte invokePrim(Object arg, byte arg0, byte arg1);}
static public interface OSSB{byte invokePrim(Object arg, short arg0, short arg1);}
static public interface OIIB{byte invokePrim(Object arg, int arg0, int arg1);}
static public interface OFFB{byte invokePrim(Object arg, float arg0, float arg1);}
static public interface OXXB{byte invokePrim(Object arg, boolean arg0, boolean arg1);}
static public interface OCCB{byte invokePrim(Object arg, char arg0, char arg1);}
static public interface OLLB{byte invokePrim(Object arg, long arg0, long arg1);}
static public interface ODDB{byte invokePrim(Object arg, double arg0, double arg1);}
static public interface OOOB{byte invokePrim(Object arg, Object arg0, Object arg1);}

static public interface OBBS{short invokePrim(Object arg, byte arg0, byte arg1);}
static public interface OSSS{short invokePrim(Object arg, short arg0, short arg1);}
static public interface OIIS{short invokePrim(Object arg, int arg0, int arg1);}
static public interface OFFS{short invokePrim(Object arg, float arg0, float arg1);}
static public interface OXXS{short invokePrim(Object arg, boolean arg0, boolean arg1);}
static public interface OCCS{short invokePrim(Object arg, char arg0, char arg1);}
static public interface OLLS{short invokePrim(Object arg, long arg0, long arg1);}
static public interface ODDS{short invokePrim(Object arg, double arg0, double arg1);}
static public interface OOOS{short invokePrim(Object arg, Object arg0, Object arg1);}

static public interface OBBI{int invokePrim(Object arg, byte arg0, byte arg1);}
static public interface OSSI{int invokePrim(Object arg, short arg0, short arg1);}
static public interface OIII{int invokePrim(Object arg, int arg0, int arg1);}
static public interface OFFI{int invokePrim(Object arg, float arg0, float arg1);}
static public interface OXXI{int invokePrim(Object arg, boolean arg0, boolean arg1);}
static public interface OCCI{int invokePrim(Object arg, char arg0, char arg1);}
static public interface OLLI{int invokePrim(Object arg, long arg0, long arg1);}
static public interface ODDI{int invokePrim(Object arg, double arg0, double arg1);}
static public interface OOOI{int invokePrim(Object arg, Object arg0, Object arg1);}

static public interface OBBF{float invokePrim(Object arg, byte arg0, byte arg1);}
static public interface OSSF{float invokePrim(Object arg, short arg0, short arg1);}
static public interface OIIF{float invokePrim(Object arg, int arg0, int arg1);}
static public interface OFFF{float invokePrim(Object arg, float arg0, float arg1);}
static public interface OXXF{float invokePrim(Object arg, boolean arg0, boolean arg1);}
static public interface OCCF{float invokePrim(Object arg, char arg0, char arg1);}
static public interface OLLF{float invokePrim(Object arg, long arg0, long arg1);}
static public interface ODDF{float invokePrim(Object arg, double arg0, double arg1);}
static public interface OOOF{float invokePrim(Object arg, Object arg0, Object arg1);}

static public interface OBBX{boolean invokePrim(Object arg, byte arg0, byte arg1);}
static public interface OSSX{boolean invokePrim(Object arg, short arg0, short arg1);}
static public interface OIIX{boolean invokePrim(Object arg, int arg0, int arg1);}
static public interface OFFX{boolean invokePrim(Object arg, float arg0, float arg1);}
static public interface OXXX{boolean invokePrim(Object arg, boolean arg0, boolean arg1);}
static public interface OCCX{boolean invokePrim(Object arg, char arg0, char arg1);}
static public interface OLLX{boolean invokePrim(Object arg, long arg0, long arg1);}
static public interface ODDX{boolean invokePrim(Object arg, double arg0, double arg1);}
static public interface OOOX{boolean invokePrim(Object arg, Object arg0, Object arg1);}

static public interface OBBC{char invokePrim(Object arg, byte arg0, byte arg1);}
static public interface OSSC{char invokePrim(Object arg, short arg0, short arg1);}
static public interface OIIC{char invokePrim(Object arg, int arg0, int arg1);}
static public interface OFFC{char invokePrim(Object arg, float arg0, float arg1);}
static public interface OXXC{char invokePrim(Object arg, boolean arg0, boolean arg1);}
static public interface OCCC{char invokePrim(Object arg, char arg0, char arg1);}
static public interface OLLC{char invokePrim(Object arg, long arg0, long arg1);}
static public interface ODDC{char invokePrim(Object arg, double arg0, double arg1);}
static public interface OOOC{char invokePrim(Object arg, Object arg0, Object arg1);}

static public interface OBBL{long invokePrim(Object arg, byte arg0, byte arg1);}
static public interface OSSL{long invokePrim(Object arg, short arg0, short arg1);}
static public interface OIIL{long invokePrim(Object arg, int arg0, int arg1);}
static public interface OFFL{long invokePrim(Object arg, float arg0, float arg1);}
static public interface OXXL{long invokePrim(Object arg, boolean arg0, boolean arg1);}
static public interface OCCL{long invokePrim(Object arg, char arg0, char arg1);}

static public interface OBBD{double invokePrim(Object arg, byte arg0, byte arg1);}
static public interface OSSD{double invokePrim(Object arg, short arg0, short arg1);}
static public interface OIID{double invokePrim(Object arg, int arg0, int arg1);}
static public interface OFFD{double invokePrim(Object arg, float arg0, float arg1);}
static public interface OXXD{double invokePrim(Object arg, boolean arg0, boolean arg1);}
static public interface OCCD{double invokePrim(Object arg, char arg0, char arg1);}

static public interface OBBO{Object invokePrim(Object arg, byte arg0, byte arg1);}
static public interface OSSO{Object invokePrim(Object arg, short arg0, short arg1);}
static public interface OIIO{Object invokePrim(Object arg, int arg0, int arg1);}
static public interface OFFO{Object invokePrim(Object arg, float arg0, float arg1);}
static public interface OXXO{Object invokePrim(Object arg, boolean arg0, boolean arg1);}
static public interface OCCO{Object invokePrim(Object arg, char arg0, char arg1);}

static public interface L{long invokePrim();}
static public interface D{double invokePrim();}
static public interface OL{long invokePrim(Object arg0);}
static public interface OD{double invokePrim(Object arg0);}
static public interface LO{Object invokePrim(long arg0);}
static public interface LL{long invokePrim(long arg0);}
static public interface LD{double invokePrim(long arg0);}
static public interface DO{Object invokePrim(double arg0);}
static public interface DL{long invokePrim(double arg0);}
static public interface DD{double invokePrim(double arg0);}
static public interface OOL{long invokePrim(Object arg0, Object arg1);}
static public interface OOD{double invokePrim(Object arg0, Object arg1);}
static public interface OLO{Object invokePrim(Object arg0, long arg1);}
static public interface OLL{long invokePrim(Object arg0, long arg1);}
static public interface OLD{double invokePrim(Object arg0, long arg1);}
static public interface ODO{Object invokePrim(Object arg0, double arg1);}
static public interface ODL{long invokePrim(Object arg0, double arg1);}
static public interface ODD{double invokePrim(Object arg0, double arg1);}
static public interface LOO{Object invokePrim(long arg0, Object arg1);}
static public interface LOL{long invokePrim(long arg0, Object arg1);}
static public interface LOD{double invokePrim(long arg0, Object arg1);}
static public interface LLO{Object invokePrim(long arg0, long arg1);}
static public interface LLL{long invokePrim(long arg0, long arg1);}
static public interface LLD{double invokePrim(long arg0, long arg1);}
static public interface LDO{Object invokePrim(long arg0, double arg1);}
static public interface LDL{long invokePrim(long arg0, double arg1);}
static public interface LDD{double invokePrim(long arg0, double arg1);}
static public interface DOO{Object invokePrim(double arg0, Object arg1);}
static public interface DOL{long invokePrim(double arg0, Object arg1);}
static public interface DOD{double invokePrim(double arg0, Object arg1);}
static public interface DLO{Object invokePrim(double arg0, long arg1);}
static public interface DLL{long invokePrim(double arg0, long arg1);}
static public interface DLD{double invokePrim(double arg0, long arg1);}
static public interface DDO{Object invokePrim(double arg0, double arg1);}
static public interface DDL{long invokePrim(double arg0, double arg1);}
static public interface DDD{double invokePrim(double arg0, double arg1);}
static public interface OOOL{long invokePrim(Object arg0, Object arg1, Object arg2);}
static public interface OOOD{double invokePrim(Object arg0, Object arg1, Object arg2);}
static public interface OOLO{Object invokePrim(Object arg0, Object arg1, long arg2);}
static public interface OOLL{long invokePrim(Object arg0, Object arg1, long arg2);}
static public interface OOLD{double invokePrim(Object arg0, Object arg1, long arg2);}
static public interface OODO{Object invokePrim(Object arg0, Object arg1, double arg2);}
static public interface OODL{long invokePrim(Object arg0, Object arg1, double arg2);}
static public interface OODD{double invokePrim(Object arg0, Object arg1, double arg2);}
static public interface OLOO{Object invokePrim(Object arg0, long arg1, Object arg2);}
static public interface OLOL{long invokePrim(Object arg0, long arg1, Object arg2);}
static public interface OLOD{double invokePrim(Object arg0, long arg1, Object arg2);}
static public interface OLLO{Object invokePrim(Object arg0, long arg1, long arg2);}
static public interface OLLL{long invokePrim(Object arg0, long arg1, long arg2);}
static public interface OLLD{double invokePrim(Object arg0, long arg1, long arg2);}
static public interface OLDO{Object invokePrim(Object arg0, long arg1, double arg2);}
static public interface OLDL{long invokePrim(Object arg0, long arg1, double arg2);}
static public interface OLDD{double invokePrim(Object arg0, long arg1, double arg2);}
static public interface ODOO{Object invokePrim(Object arg0, double arg1, Object arg2);}
static public interface ODOL{long invokePrim(Object arg0, double arg1, Object arg2);}
static public interface ODOD{double invokePrim(Object arg0, double arg1, Object arg2);}
static public interface ODLO{Object invokePrim(Object arg0, double arg1, long arg2);}
static public interface ODLL{long invokePrim(Object arg0, double arg1, long arg2);}
static public interface ODLD{double invokePrim(Object arg0, double arg1, long arg2);}
static public interface ODDO{Object invokePrim(Object arg0, double arg1, double arg2);}
static public interface ODDL{long invokePrim(Object arg0, double arg1, double arg2);}
static public interface ODDD{double invokePrim(Object arg0, double arg1, double arg2);}
static public interface LOOO{Object invokePrim(long arg0, Object arg1, Object arg2);}
static public interface LOOL{long invokePrim(long arg0, Object arg1, Object arg2);}
static public interface LOOD{double invokePrim(long arg0, Object arg1, Object arg2);}
static public interface LOLO{Object invokePrim(long arg0, Object arg1, long arg2);}
static public interface LOLL{long invokePrim(long arg0, Object arg1, long arg2);}
static public interface LOLD{double invokePrim(long arg0, Object arg1, long arg2);}
static public interface LODO{Object invokePrim(long arg0, Object arg1, double arg2);}
static public interface LODL{long invokePrim(long arg0, Object arg1, double arg2);}
static public interface LODD{double invokePrim(long arg0, Object arg1, double arg2);}
static public interface LLOO{Object invokePrim(long arg0, long arg1, Object arg2);}
static public interface LLOL{long invokePrim(long arg0, long arg1, Object arg2);}
static public interface LLOD{double invokePrim(long arg0, long arg1, Object arg2);}
static public interface LLLO{Object invokePrim(long arg0, long arg1, long arg2);}
static public interface LLLL{long invokePrim(long arg0, long arg1, long arg2);}
static public interface LLLD{double invokePrim(long arg0, long arg1, long arg2);}
static public interface LLDO{Object invokePrim(long arg0, long arg1, double arg2);}
static public interface LLDL{long invokePrim(long arg0, long arg1, double arg2);}
static public interface LLDD{double invokePrim(long arg0, long arg1, double arg2);}
static public interface LDOO{Object invokePrim(long arg0, double arg1, Object arg2);}
static public interface LDOL{long invokePrim(long arg0, double arg1, Object arg2);}
static public interface LDOD{double invokePrim(long arg0, double arg1, Object arg2);}
static public interface LDLO{Object invokePrim(long arg0, double arg1, long arg2);}
static public interface LDLL{long invokePrim(long arg0, double arg1, long arg2);}
static public interface LDLD{double invokePrim(long arg0, double arg1, long arg2);}
static public interface LDDO{Object invokePrim(long arg0, double arg1, double arg2);}
static public interface LDDL{long invokePrim(long arg0, double arg1, double arg2);}
static public interface LDDD{double invokePrim(long arg0, double arg1, double arg2);}
static public interface DOOO{Object invokePrim(double arg0, Object arg1, Object arg2);}
static public interface DOOL{long invokePrim(double arg0, Object arg1, Object arg2);}
static public interface DOOD{double invokePrim(double arg0, Object arg1, Object arg2);}
static public interface DOLO{Object invokePrim(double arg0, Object arg1, long arg2);}
static public interface DOLL{long invokePrim(double arg0, Object arg1, long arg2);}
static public interface DOLD{double invokePrim(double arg0, Object arg1, long arg2);}
static public interface DODO{Object invokePrim(double arg0, Object arg1, double arg2);}
static public interface DODL{long invokePrim(double arg0, Object arg1, double arg2);}
static public interface DODD{double invokePrim(double arg0, Object arg1, double arg2);}
static public interface DLOO{Object invokePrim(double arg0, long arg1, Object arg2);}
static public interface DLOL{long invokePrim(double arg0, long arg1, Object arg2);}
static public interface DLOD{double invokePrim(double arg0, long arg1, Object arg2);}
static public interface DLLO{Object invokePrim(double arg0, long arg1, long arg2);}
static public interface DLLL{long invokePrim(double arg0, long arg1, long arg2);}
static public interface DLLD{double invokePrim(double arg0, long arg1, long arg2);}
static public interface DLDO{Object invokePrim(double arg0, long arg1, double arg2);}
static public interface DLDL{long invokePrim(double arg0, long arg1, double arg2);}
static public interface DLDD{double invokePrim(double arg0, long arg1, double arg2);}
static public interface DDOO{Object invokePrim(double arg0, double arg1, Object arg2);}
static public interface DDOL{long invokePrim(double arg0, double arg1, Object arg2);}
static public interface DDOD{double invokePrim(double arg0, double arg1, Object arg2);}
static public interface DDLO{Object invokePrim(double arg0, double arg1, long arg2);}
static public interface DDLL{long invokePrim(double arg0, double arg1, long arg2);}
static public interface DDLD{double invokePrim(double arg0, double arg1, long arg2);}
static public interface DDDO{Object invokePrim(double arg0, double arg1, double arg2);}
static public interface DDDL{long invokePrim(double arg0, double arg1, double arg2);}
static public interface DDDD{double invokePrim(double arg0, double arg1, double arg2);}
static public interface OOOOL{long invokePrim(Object arg0, Object arg1, Object arg2, Object arg3);}
static public interface OOOOD{double invokePrim(Object arg0, Object arg1, Object arg2, Object arg3);}
static public interface OOOLO{Object invokePrim(Object arg0, Object arg1, Object arg2, long arg3);}
static public interface OOOLL{long invokePrim(Object arg0, Object arg1, Object arg2, long arg3);}
static public interface OOOLD{double invokePrim(Object arg0, Object arg1, Object arg2, long arg3);}
static public interface OOODO{Object invokePrim(Object arg0, Object arg1, Object arg2, double arg3);}
static public interface OOODL{long invokePrim(Object arg0, Object arg1, Object arg2, double arg3);}
static public interface OOODD{double invokePrim(Object arg0, Object arg1, Object arg2, double arg3);}
static public interface OOLOO{Object invokePrim(Object arg0, Object arg1, long arg2, Object arg3);}
static public interface OOLOL{long invokePrim(Object arg0, Object arg1, long arg2, Object arg3);}
static public interface OOLOD{double invokePrim(Object arg0, Object arg1, long arg2, Object arg3);}
static public interface OOLLO{Object invokePrim(Object arg0, Object arg1, long arg2, long arg3);}
static public interface OOLLL{long invokePrim(Object arg0, Object arg1, long arg2, long arg3);}
static public interface OOLLD{double invokePrim(Object arg0, Object arg1, long arg2, long arg3);}
static public interface OOLDO{Object invokePrim(Object arg0, Object arg1, long arg2, double arg3);}
static public interface OOLDL{long invokePrim(Object arg0, Object arg1, long arg2, double arg3);}
static public interface OOLDD{double invokePrim(Object arg0, Object arg1, long arg2, double arg3);}
static public interface OODOO{Object invokePrim(Object arg0, Object arg1, double arg2, Object arg3);}
static public interface OODOL{long invokePrim(Object arg0, Object arg1, double arg2, Object arg3);}
static public interface OODOD{double invokePrim(Object arg0, Object arg1, double arg2, Object arg3);}
static public interface OODLO{Object invokePrim(Object arg0, Object arg1, double arg2, long arg3);}
static public interface OODLL{long invokePrim(Object arg0, Object arg1, double arg2, long arg3);}
static public interface OODLD{double invokePrim(Object arg0, Object arg1, double arg2, long arg3);}
static public interface OODDO{Object invokePrim(Object arg0, Object arg1, double arg2, double arg3);}
static public interface OODDL{long invokePrim(Object arg0, Object arg1, double arg2, double arg3);}
static public interface OODDD{double invokePrim(Object arg0, Object arg1, double arg2, double arg3);}
static public interface OLOOO{Object invokePrim(Object arg0, long arg1, Object arg2, Object arg3);}
static public interface OLOOL{long invokePrim(Object arg0, long arg1, Object arg2, Object arg3);}
static public interface OLOOD{double invokePrim(Object arg0, long arg1, Object arg2, Object arg3);}
static public interface OLOLO{Object invokePrim(Object arg0, long arg1, Object arg2, long arg3);}
static public interface OLOLL{long invokePrim(Object arg0, long arg1, Object arg2, long arg3);}
static public interface OLOLD{double invokePrim(Object arg0, long arg1, Object arg2, long arg3);}
static public interface OLODO{Object invokePrim(Object arg0, long arg1, Object arg2, double arg3);}
static public interface OLODL{long invokePrim(Object arg0, long arg1, Object arg2, double arg3);}
static public interface OLODD{double invokePrim(Object arg0, long arg1, Object arg2, double arg3);}
static public interface OLLOO{Object invokePrim(Object arg0, long arg1, long arg2, Object arg3);}
static public interface OLLOL{long invokePrim(Object arg0, long arg1, long arg2, Object arg3);}
static public interface OLLOD{double invokePrim(Object arg0, long arg1, long arg2, Object arg3);}
static public interface OLLLO{Object invokePrim(Object arg0, long arg1, long arg2, long arg3);}
static public interface OLLLL{long invokePrim(Object arg0, long arg1, long arg2, long arg3);}
static public interface OLLLD{double invokePrim(Object arg0, long arg1, long arg2, long arg3);}
static public interface OLLDO{Object invokePrim(Object arg0, long arg1, long arg2, double arg3);}
static public interface OLLDL{long invokePrim(Object arg0, long arg1, long arg2, double arg3);}
static public interface OLLDD{double invokePrim(Object arg0, long arg1, long arg2, double arg3);}
static public interface OLDOO{Object invokePrim(Object arg0, long arg1, double arg2, Object arg3);}
static public interface OLDOL{long invokePrim(Object arg0, long arg1, double arg2, Object arg3);}
static public interface OLDOD{double invokePrim(Object arg0, long arg1, double arg2, Object arg3);}
static public interface OLDLO{Object invokePrim(Object arg0, long arg1, double arg2, long arg3);}
static public interface OLDLL{long invokePrim(Object arg0, long arg1, double arg2, long arg3);}
static public interface OLDLD{double invokePrim(Object arg0, long arg1, double arg2, long arg3);}
static public interface OLDDO{Object invokePrim(Object arg0, long arg1, double arg2, double arg3);}
static public interface OLDDL{long invokePrim(Object arg0, long arg1, double arg2, double arg3);}
static public interface OLDDD{double invokePrim(Object arg0, long arg1, double arg2, double arg3);}
static public interface ODOOO{Object invokePrim(Object arg0, double arg1, Object arg2, Object arg3);}
static public interface ODOOL{long invokePrim(Object arg0, double arg1, Object arg2, Object arg3);}
static public interface ODOOD{double invokePrim(Object arg0, double arg1, Object arg2, Object arg3);}
static public interface ODOLO{Object invokePrim(Object arg0, double arg1, Object arg2, long arg3);}
static public interface ODOLL{long invokePrim(Object arg0, double arg1, Object arg2, long arg3);}
static public interface ODOLD{double invokePrim(Object arg0, double arg1, Object arg2, long arg3);}
static public interface ODODO{Object invokePrim(Object arg0, double arg1, Object arg2, double arg3);}
static public interface ODODL{long invokePrim(Object arg0, double arg1, Object arg2, double arg3);}
static public interface ODODD{double invokePrim(Object arg0, double arg1, Object arg2, double arg3);}
static public interface ODLOO{Object invokePrim(Object arg0, double arg1, long arg2, Object arg3);}
static public interface ODLOL{long invokePrim(Object arg0, double arg1, long arg2, Object arg3);}
static public interface ODLOD{double invokePrim(Object arg0, double arg1, long arg2, Object arg3);}
static public interface ODLLO{Object invokePrim(Object arg0, double arg1, long arg2, long arg3);}
static public interface ODLLL{long invokePrim(Object arg0, double arg1, long arg2, long arg3);}
static public interface ODLLD{double invokePrim(Object arg0, double arg1, long arg2, long arg3);}
static public interface ODLDO{Object invokePrim(Object arg0, double arg1, long arg2, double arg3);}
static public interface ODLDL{long invokePrim(Object arg0, double arg1, long arg2, double arg3);}
static public interface ODLDD{double invokePrim(Object arg0, double arg1, long arg2, double arg3);}
static public interface ODDOO{Object invokePrim(Object arg0, double arg1, double arg2, Object arg3);}
static public interface ODDOL{long invokePrim(Object arg0, double arg1, double arg2, Object arg3);}
static public interface ODDOD{double invokePrim(Object arg0, double arg1, double arg2, Object arg3);}
static public interface ODDLO{Object invokePrim(Object arg0, double arg1, double arg2, long arg3);}
static public interface ODDLL{long invokePrim(Object arg0, double arg1, double arg2, long arg3);}
static public interface ODDLD{double invokePrim(Object arg0, double arg1, double arg2, long arg3);}
static public interface ODDDO{Object invokePrim(Object arg0, double arg1, double arg2, double arg3);}
static public interface ODDDL{long invokePrim(Object arg0, double arg1, double arg2, double arg3);}
static public interface ODDDD{double invokePrim(Object arg0, double arg1, double arg2, double arg3);}
static public interface LOOOO{Object invokePrim(long arg0, Object arg1, Object arg2, Object arg3);}
static public interface LOOOL{long invokePrim(long arg0, Object arg1, Object arg2, Object arg3);}
static public interface LOOOD{double invokePrim(long arg0, Object arg1, Object arg2, Object arg3);}
static public interface LOOLO{Object invokePrim(long arg0, Object arg1, Object arg2, long arg3);}
static public interface LOOLL{long invokePrim(long arg0, Object arg1, Object arg2, long arg3);}
static public interface LOOLD{double invokePrim(long arg0, Object arg1, Object arg2, long arg3);}
static public interface LOODO{Object invokePrim(long arg0, Object arg1, Object arg2, double arg3);}
static public interface LOODL{long invokePrim(long arg0, Object arg1, Object arg2, double arg3);}
static public interface LOODD{double invokePrim(long arg0, Object arg1, Object arg2, double arg3);}
static public interface LOLOO{Object invokePrim(long arg0, Object arg1, long arg2, Object arg3);}
static public interface LOLOL{long invokePrim(long arg0, Object arg1, long arg2, Object arg3);}
static public interface LOLOD{double invokePrim(long arg0, Object arg1, long arg2, Object arg3);}
static public interface LOLLO{Object invokePrim(long arg0, Object arg1, long arg2, long arg3);}
static public interface LOLLL{long invokePrim(long arg0, Object arg1, long arg2, long arg3);}
static public interface LOLLD{double invokePrim(long arg0, Object arg1, long arg2, long arg3);}
static public interface LOLDO{Object invokePrim(long arg0, Object arg1, long arg2, double arg3);}
static public interface LOLDL{long invokePrim(long arg0, Object arg1, long arg2, double arg3);}
static public interface LOLDD{double invokePrim(long arg0, Object arg1, long arg2, double arg3);}
static public interface LODOO{Object invokePrim(long arg0, Object arg1, double arg2, Object arg3);}
static public interface LODOL{long invokePrim(long arg0, Object arg1, double arg2, Object arg3);}
static public interface LODOD{double invokePrim(long arg0, Object arg1, double arg2, Object arg3);}
static public interface LODLO{Object invokePrim(long arg0, Object arg1, double arg2, long arg3);}
static public interface LODLL{long invokePrim(long arg0, Object arg1, double arg2, long arg3);}
static public interface LODLD{double invokePrim(long arg0, Object arg1, double arg2, long arg3);}
static public interface LODDO{Object invokePrim(long arg0, Object arg1, double arg2, double arg3);}
static public interface LODDL{long invokePrim(long arg0, Object arg1, double arg2, double arg3);}
static public interface LODDD{double invokePrim(long arg0, Object arg1, double arg2, double arg3);}
static public interface LLOOO{Object invokePrim(long arg0, long arg1, Object arg2, Object arg3);}
static public interface LLOOL{long invokePrim(long arg0, long arg1, Object arg2, Object arg3);}
static public interface LLOOD{double invokePrim(long arg0, long arg1, Object arg2, Object arg3);}
static public interface LLOLO{Object invokePrim(long arg0, long arg1, Object arg2, long arg3);}
static public interface LLOLL{long invokePrim(long arg0, long arg1, Object arg2, long arg3);}
static public interface LLOLD{double invokePrim(long arg0, long arg1, Object arg2, long arg3);}
static public interface LLODO{Object invokePrim(long arg0, long arg1, Object arg2, double arg3);}
static public interface LLODL{long invokePrim(long arg0, long arg1, Object arg2, double arg3);}
static public interface LLODD{double invokePrim(long arg0, long arg1, Object arg2, double arg3);}
static public interface LLLOO{Object invokePrim(long arg0, long arg1, long arg2, Object arg3);}
static public interface LLLOL{long invokePrim(long arg0, long arg1, long arg2, Object arg3);}
static public interface LLLOD{double invokePrim(long arg0, long arg1, long arg2, Object arg3);}
static public interface LLLLO{Object invokePrim(long arg0, long arg1, long arg2, long arg3);}
static public interface LLLLL{long invokePrim(long arg0, long arg1, long arg2, long arg3);}
static public interface LLLLD{double invokePrim(long arg0, long arg1, long arg2, long arg3);}
static public interface LLLDO{Object invokePrim(long arg0, long arg1, long arg2, double arg3);}
static public interface LLLDL{long invokePrim(long arg0, long arg1, long arg2, double arg3);}
static public interface LLLDD{double invokePrim(long arg0, long arg1, long arg2, double arg3);}
static public interface LLDOO{Object invokePrim(long arg0, long arg1, double arg2, Object arg3);}
static public interface LLDOL{long invokePrim(long arg0, long arg1, double arg2, Object arg3);}
static public interface LLDOD{double invokePrim(long arg0, long arg1, double arg2, Object arg3);}
static public interface LLDLO{Object invokePrim(long arg0, long arg1, double arg2, long arg3);}
static public interface LLDLL{long invokePrim(long arg0, long arg1, double arg2, long arg3);}
static public interface LLDLD{double invokePrim(long arg0, long arg1, double arg2, long arg3);}
static public interface LLDDO{Object invokePrim(long arg0, long arg1, double arg2, double arg3);}
static public interface LLDDL{long invokePrim(long arg0, long arg1, double arg2, double arg3);}
static public interface LLDDD{double invokePrim(long arg0, long arg1, double arg2, double arg3);}
static public interface LDOOO{Object invokePrim(long arg0, double arg1, Object arg2, Object arg3);}
static public interface LDOOL{long invokePrim(long arg0, double arg1, Object arg2, Object arg3);}
static public interface LDOOD{double invokePrim(long arg0, double arg1, Object arg2, Object arg3);}
static public interface LDOLO{Object invokePrim(long arg0, double arg1, Object arg2, long arg3);}
static public interface LDOLL{long invokePrim(long arg0, double arg1, Object arg2, long arg3);}
static public interface LDOLD{double invokePrim(long arg0, double arg1, Object arg2, long arg3);}
static public interface LDODO{Object invokePrim(long arg0, double arg1, Object arg2, double arg3);}
static public interface LDODL{long invokePrim(long arg0, double arg1, Object arg2, double arg3);}
static public interface LDODD{double invokePrim(long arg0, double arg1, Object arg2, double arg3);}
static public interface LDLOO{Object invokePrim(long arg0, double arg1, long arg2, Object arg3);}
static public interface LDLOL{long invokePrim(long arg0, double arg1, long arg2, Object arg3);}
static public interface LDLOD{double invokePrim(long arg0, double arg1, long arg2, Object arg3);}
static public interface LDLLO{Object invokePrim(long arg0, double arg1, long arg2, long arg3);}
static public interface LDLLL{long invokePrim(long arg0, double arg1, long arg2, long arg3);}
static public interface LDLLD{double invokePrim(long arg0, double arg1, long arg2, long arg3);}
static public interface LDLDO{Object invokePrim(long arg0, double arg1, long arg2, double arg3);}
static public interface LDLDL{long invokePrim(long arg0, double arg1, long arg2, double arg3);}
static public interface LDLDD{double invokePrim(long arg0, double arg1, long arg2, double arg3);}
static public interface LDDOO{Object invokePrim(long arg0, double arg1, double arg2, Object arg3);}
static public interface LDDOL{long invokePrim(long arg0, double arg1, double arg2, Object arg3);}
static public interface LDDOD{double invokePrim(long arg0, double arg1, double arg2, Object arg3);}
static public interface LDDLO{Object invokePrim(long arg0, double arg1, double arg2, long arg3);}
static public interface LDDLL{long invokePrim(long arg0, double arg1, double arg2, long arg3);}
static public interface LDDLD{double invokePrim(long arg0, double arg1, double arg2, long arg3);}
static public interface LDDDO{Object invokePrim(long arg0, double arg1, double arg2, double arg3);}
static public interface LDDDL{long invokePrim(long arg0, double arg1, double arg2, double arg3);}
static public interface LDDDD{double invokePrim(long arg0, double arg1, double arg2, double arg3);}
static public interface DOOOO{Object invokePrim(double arg0, Object arg1, Object arg2, Object arg3);}
static public interface DOOOL{long invokePrim(double arg0, Object arg1, Object arg2, Object arg3);}
static public interface DOOOD{double invokePrim(double arg0, Object arg1, Object arg2, Object arg3);}
static public interface DOOLO{Object invokePrim(double arg0, Object arg1, Object arg2, long arg3);}
static public interface DOOLL{long invokePrim(double arg0, Object arg1, Object arg2, long arg3);}
static public interface DOOLD{double invokePrim(double arg0, Object arg1, Object arg2, long arg3);}
static public interface DOODO{Object invokePrim(double arg0, Object arg1, Object arg2, double arg3);}
static public interface DOODL{long invokePrim(double arg0, Object arg1, Object arg2, double arg3);}
static public interface DOODD{double invokePrim(double arg0, Object arg1, Object arg2, double arg3);}
static public interface DOLOO{Object invokePrim(double arg0, Object arg1, long arg2, Object arg3);}
static public interface DOLOL{long invokePrim(double arg0, Object arg1, long arg2, Object arg3);}
static public interface DOLOD{double invokePrim(double arg0, Object arg1, long arg2, Object arg3);}
static public interface DOLLO{Object invokePrim(double arg0, Object arg1, long arg2, long arg3);}
static public interface DOLLL{long invokePrim(double arg0, Object arg1, long arg2, long arg3);}
static public interface DOLLD{double invokePrim(double arg0, Object arg1, long arg2, long arg3);}
static public interface DOLDO{Object invokePrim(double arg0, Object arg1, long arg2, double arg3);}
static public interface DOLDL{long invokePrim(double arg0, Object arg1, long arg2, double arg3);}
static public interface DOLDD{double invokePrim(double arg0, Object arg1, long arg2, double arg3);}
static public interface DODOO{Object invokePrim(double arg0, Object arg1, double arg2, Object arg3);}
static public interface DODOL{long invokePrim(double arg0, Object arg1, double arg2, Object arg3);}
static public interface DODOD{double invokePrim(double arg0, Object arg1, double arg2, Object arg3);}
static public interface DODLO{Object invokePrim(double arg0, Object arg1, double arg2, long arg3);}
static public interface DODLL{long invokePrim(double arg0, Object arg1, double arg2, long arg3);}
static public interface DODLD{double invokePrim(double arg0, Object arg1, double arg2, long arg3);}
static public interface DODDO{Object invokePrim(double arg0, Object arg1, double arg2, double arg3);}
static public interface DODDL{long invokePrim(double arg0, Object arg1, double arg2, double arg3);}
static public interface DODDD{double invokePrim(double arg0, Object arg1, double arg2, double arg3);}
static public interface DLOOO{Object invokePrim(double arg0, long arg1, Object arg2, Object arg3);}
static public interface DLOOL{long invokePrim(double arg0, long arg1, Object arg2, Object arg3);}
static public interface DLOOD{double invokePrim(double arg0, long arg1, Object arg2, Object arg3);}
static public interface DLOLO{Object invokePrim(double arg0, long arg1, Object arg2, long arg3);}
static public interface DLOLL{long invokePrim(double arg0, long arg1, Object arg2, long arg3);}
static public interface DLOLD{double invokePrim(double arg0, long arg1, Object arg2, long arg3);}
static public interface DLODO{Object invokePrim(double arg0, long arg1, Object arg2, double arg3);}
static public interface DLODL{long invokePrim(double arg0, long arg1, Object arg2, double arg3);}
static public interface DLODD{double invokePrim(double arg0, long arg1, Object arg2, double arg3);}
static public interface DLLOO{Object invokePrim(double arg0, long arg1, long arg2, Object arg3);}
static public interface DLLOL{long invokePrim(double arg0, long arg1, long arg2, Object arg3);}
static public interface DLLOD{double invokePrim(double arg0, long arg1, long arg2, Object arg3);}
static public interface DLLLO{Object invokePrim(double arg0, long arg1, long arg2, long arg3);}
static public interface DLLLL{long invokePrim(double arg0, long arg1, long arg2, long arg3);}
static public interface DLLLD{double invokePrim(double arg0, long arg1, long arg2, long arg3);}
static public interface DLLDO{Object invokePrim(double arg0, long arg1, long arg2, double arg3);}
static public interface DLLDL{long invokePrim(double arg0, long arg1, long arg2, double arg3);}
static public interface DLLDD{double invokePrim(double arg0, long arg1, long arg2, double arg3);}
static public interface DLDOO{Object invokePrim(double arg0, long arg1, double arg2, Object arg3);}
static public interface DLDOL{long invokePrim(double arg0, long arg1, double arg2, Object arg3);}
static public interface DLDOD{double invokePrim(double arg0, long arg1, double arg2, Object arg3);}
static public interface DLDLO{Object invokePrim(double arg0, long arg1, double arg2, long arg3);}
static public interface DLDLL{long invokePrim(double arg0, long arg1, double arg2, long arg3);}
static public interface DLDLD{double invokePrim(double arg0, long arg1, double arg2, long arg3);}
static public interface DLDDO{Object invokePrim(double arg0, long arg1, double arg2, double arg3);}
static public interface DLDDL{long invokePrim(double arg0, long arg1, double arg2, double arg3);}
static public interface DLDDD{double invokePrim(double arg0, long arg1, double arg2, double arg3);}
static public interface DDOOO{Object invokePrim(double arg0, double arg1, Object arg2, Object arg3);}
static public interface DDOOL{long invokePrim(double arg0, double arg1, Object arg2, Object arg3);}
static public interface DDOOD{double invokePrim(double arg0, double arg1, Object arg2, Object arg3);}
static public interface DDOLO{Object invokePrim(double arg0, double arg1, Object arg2, long arg3);}
static public interface DDOLL{long invokePrim(double arg0, double arg1, Object arg2, long arg3);}
static public interface DDOLD{double invokePrim(double arg0, double arg1, Object arg2, long arg3);}
static public interface DDODO{Object invokePrim(double arg0, double arg1, Object arg2, double arg3);}
static public interface DDODL{long invokePrim(double arg0, double arg1, Object arg2, double arg3);}
static public interface DDODD{double invokePrim(double arg0, double arg1, Object arg2, double arg3);}
static public interface DDLOO{Object invokePrim(double arg0, double arg1, long arg2, Object arg3);}
static public interface DDLOL{long invokePrim(double arg0, double arg1, long arg2, Object arg3);}
static public interface DDLOD{double invokePrim(double arg0, double arg1, long arg2, Object arg3);}
static public interface DDLLO{Object invokePrim(double arg0, double arg1, long arg2, long arg3);}
static public interface DDLLL{long invokePrim(double arg0, double arg1, long arg2, long arg3);}
static public interface DDLLD{double invokePrim(double arg0, double arg1, long arg2, long arg3);}
static public interface DDLDO{Object invokePrim(double arg0, double arg1, long arg2, double arg3);}
static public interface DDLDL{long invokePrim(double arg0, double arg1, long arg2, double arg3);}
static public interface DDLDD{double invokePrim(double arg0, double arg1, long arg2, double arg3);}
static public interface DDDOO{Object invokePrim(double arg0, double arg1, double arg2, Object arg3);}
static public interface DDDOL{long invokePrim(double arg0, double arg1, double arg2, Object arg3);}
static public interface DDDOD{double invokePrim(double arg0, double arg1, double arg2, Object arg3);}
static public interface DDDLO{Object invokePrim(double arg0, double arg1, double arg2, long arg3);}
static public interface DDDLL{long invokePrim(double arg0, double arg1, double arg2, long arg3);}
static public interface DDDLD{double invokePrim(double arg0, double arg1, double arg2, long arg3);}
static public interface DDDDO{Object invokePrim(double arg0, double arg1, double arg2, double arg3);}
static public interface DDDDL{long invokePrim(double arg0, double arg1, double arg2, double arg3);}
static public interface DDDDD{double invokePrim(double arg0, double arg1, double arg2, double arg3);}
}
