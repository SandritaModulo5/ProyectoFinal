package Grafica;

import java.awt.Dimension;
import java.awt.Point;

public class LibreriaMatematica {
    
    public static Point getP(Point p1, Point p2, int distancia){
        Point p = new Point();
        double aa = getDistancia(p1,p2)/distancia;
        double x = p2.getX()-((p2.getX()-p1.getX())/aa);
        double y = p2.getY()-((p2.getY()-p1.getY())/aa);
        return new Point((int)x,(int)y);
    }
    
    public static double getDistancia(Point p1, Point p2) {
        double d; 
        double xx = p2.getX()-p1.getX();
        double yy = p2.getY()-p1.getY();
        d = Math.sqrt((xx*xx)+(yy*yy));
        return d;
    }
    
    private static boolean sonIguales(double a, double b)
    {
        boolean ban = false;
          if(Math.abs(a-b)<0.1)
            ban = true;
        return ban;
    }
    
    private static double getDistPuntos(int x1, int y1, int x2, int y2)
    {
        double d, a, b;
            a = (x2-x1)*(x2-x1);
            b = (y2-y1)*(y2-y1);
            d = Math.sqrt(a+b);
        return d;
    }
    
    private static int getTipoInterseccionSegmentColineales(int u1, int v1, int u2, int v2, int x1, int y1, int x2, int y2)
    {
        int res;
        double d1, d2, dism, m1, m2, m3, m4;
            // res := 1  se intersectan en un punto
            // res := 2  se intersectan en mas de un punto
            // res := 3  NOOO se intersectan

            d1 = getDistPuntos(u1,v1,u2,v2);
            d2 = getDistPuntos(x1,y1,x2,y2);

            // las 4 posibles distancias mayores
            m1 = getDistPuntos(u1,v1,x1,y1);
            m2 = getDistPuntos(u1,v1,x2,y2);
            m3 = getDistPuntos(u2,v2,x1,y1);
            m4 = getDistPuntos(u2,v2,x2,y2);

            // encontramos la distancia mayor en: dism
            dism = m1;
            if(m2>dism)
              dism = m2;
            if(m3>dism)
              dism = m3;
            if(m4>dism)
              dism = m4;

            if(sonIguales((d1+d2),dism))
            {
              res = 1;  // se intersectan en un vertice
            }
            else 
            { 
                if((d1+d2)>dism)
                {
                   res = 2;  // se intersectan en mas de un punto
                }
                else
                {
                    res = 3;  // no se intersectan
                }
            }
        return res;
    }
    
    private static Point getInterseccionSegmentColineales(int u1, int v1, int u2, int v2, int x1, int y1, int x2, int y2)
    {
        Point p = new Point();
        int res;
        double d1, d2, dism, m1, m2, m3, m4;
            // res := 1  se intersectan en un punto
            // res := 2  se intersectan en mas de un punto
            // res := 3  NOOO se intersectan

            d1 = getDistPuntos(u1,v1,u2,v2);
            d2 = getDistPuntos(x1,y1,x2,y2);

            // las 4 posibles distancias mayores
            m1 = getDistPuntos(u1,v1,x1,y1);
            m2 = getDistPuntos(u1,v1,x2,y2);
            m3 = getDistPuntos(u2,v2,x1,y1);
            m4 = getDistPuntos(u2,v2,x2,y2);

            // encontramos la distancia mayor en: dism
            dism = m1;
            if(m2>dism)
              dism = m2;
            if(m3>dism)
              dism = m3;
            if(m4>dism)
              dism = m4;

            if(sonIguales((d1+d2),dism))
            {
                res = 1;  // se intersectan en un vertice
                if (u1==x1 && v1 == y1){
                    p = new Point(x1,y1);
                }
                if (u2==x1 && v2 == y1){
                    p = new Point(x1,y1);
                }
                if (u1==x2 && v1 == y2){
                    p = new Point(x2,y2);
                }
                if (u2==x2 && v2 == y2){
                    p = new Point(x2,y2);
                }
            }
            else 
            { 
                if((d1+d2)>dism)
                {
                   res = 2;  // se intersectan en mas de un punto
                }
                else
                {
                    res = 3;  // no se intersectan
                }
            }
        return p;
    }
    
    private static boolean esSegmentoPunto(int x1, int y1, int x2, int y2)
    {
        boolean b = false;
        if((x1==x2)&&(y1==y2))
              b = true;
        return b;
    }
    
    private static boolean enDom(double a, double w1, double w2)
    {
      double n1, n2;
      boolean ban;
      ban = false;
      n1 = w1;
      n2 = w2;
      if(w1>=w2)
      {
        n1 = w2;
        n2 = w1;
      }
      if((a>=n1)&&(a<=n2))//  'a'  esta en el dominio cuando: [n1 <= a <= n2]
        ban = true;
      return  ban;
    }
    
    private static boolean estaPuntoEnSegment(int x, int y, int x1, int y1, int x2, int y2)
    {
        boolean ban;
        double d1 , d2;
        ban = false;
        d1 = getDistPuntos(x1,y1,x2,y2);
        d2 = getDistPuntos(x1,y1,x,y) + getDistPuntos(x,y,x2,y2);
        if (sonIguales(d1,d2))
            ban = true;
        return ban;
    }
    
    public static int tipoInterseccion(int u1, int v1, int u2, int v2, int x1, int y1, int x2, int y2)
    {
      int res;
      int a1,a2,b1,b2,c1,c2,dd;
      double xx,yy;
      res = -1; // cuando uno de los segmentos es un punto
      // res := 0 cuando son paralelas pero no colineales (no se intersectan)
      // res := 1 CUANDO SE INTERSECTAN EN UN PUNTO   
      // res := 2 cuando se intersectan en mas de un punto (sii colineales)
      // res := 3 cuando cuando son paralelas y colineales (no se intersectan)
      // res := 4 cuando cuando NOOOOO  son paralelas PERO NOOOOOOOOOOOOO SE INTERSECTAN
      
      if((! esSegmentoPunto(u1,v1,u2,v2))&&(! esSegmentoPunto(x1,y1,x2,y2)))
      {
        //ecuacion general de la recta...................................
        //    ax + by = c
        //...............................................................

        // coeficientes de la recta111111111111
        a1 = v1-v2;
        b1 = u2-u1;
        c1 = (u1*a1)+(v1*b1);

        // coeficientes de la recta22222222222
        a2 = y1-y2;
        b2 = x2-x1;
        c2 = (x1*a2)+(y1*b2);

        // discriminante...........................
        dd = (a1*b2)-(b1*a2);

        //analizamos paralelismo.........................
        if(dd==0) // si son paralelas.................
        {
            if(sonIguales(((b1*c2)+0.0),((c1*b2)+0.0))) // si las rectas son colineales.........
            {
                res = getTipoInterseccionSegmentColineales(u1,v1,u2,v2,x1,y1,x2,y2);
            }
            else
                res =0; // son paralelas pero no colineales  (no se intersectan)
        } 
        else 
        {  // si no son paralelas (sii se intersectan)
          // encontramos el punto de interseccion (xx,yy)
          xx = (((c1+0.0)*(b2+0.0))-((b1+0.0)*(c2+0.0)))/(dd + 0.0);
          yy = (((a1+0.0)*(c2+0.0))-((c1+0.0)*(a2+0.0)))/(dd + 0.0);

          // el punto tiene que estar dentro de los dominios en 'X' y en 'Y'
          // en los dos segmentos
          if((enDom(xx,(u1+0.0),(u2+0.0)))&&(enDom(xx,(x1+0.0),(x2+0.0)))&&(enDom(yy,(v1+0.0),(v2+0.0)))&&(enDom(yy,(y1+0.0),(y2+0.0))))  // si esta en el dominio
            res = 1;
          else
            res = 4;
        }

      }
        return res;
    }  
    
    public static Point interseccionDeSegmentos(int u1, int v1, int u2, int v2, int x1, int y1, int x2, int y2)
    {
        Point p = new Point() ;
      int res;
      int a1,a2,b1,b2,c1,c2,dd;
      double xx,yy;
      res = -1; // cuando uno de los segmentos es un punto
      // res := 0 cuando son paralelas pero no colineales (no se intersectan)
      // res := 1 CUANDO SE INTERSECTAN EN UN PUNTO   
      // res := 2 cuando se intersectan en mas de un punto (sii colineales)
      // res := 3 cuando cuando son paralelas y colineales (no se intersectan)
      // res := 4 cuando cuando NOOOOO  son paralelas PERO NOOOOOOOOOOOOO SE INTERSECTAN
      
      if((! esSegmentoPunto(u1,v1,u2,v2))&&(! esSegmentoPunto(x1,y1,x2,y2)))
      {
        //ecuacion general de la recta...................................
        //    ax + by = c
        //...............................................................

        // coeficientes de la recta111111111111
        a1 = v1-v2;
        b1 = u2-u1;
        c1 = (u1*a1)+(v1*b1);

        // coeficientes de la recta22222222222
        a2 = y1-y2;
        b2 = x2-x1;
        c2 = (x1*a2)+(y1*b2);

        // discriminante...........................
        dd = (a1*b2)-(b1*a2);

        //analizamos paralelismo.........................
        if(dd==0) // si son paralelas.................
        {
            if(sonIguales(((b1*c2)+0.0),((c1*b2)+0.0))) // si las rectas son colineales.........
            {
                p = getInterseccionSegmentColineales(u1,v1,u2,v2,x1,y1,x2,y2);
            }
            else
                res =0; // son paralelas pero no colineales  (no se intersectan)
        } 
        else 
        {  // si no son paralelas (sii se intersectan)
          // encontramos el punto de interseccion (xx,yy)
          xx = (((c1+0.0)*(b2+0.0))-((b1+0.0)*(c2+0.0)))/(dd + 0.0);
          yy = (((a1+0.0)*(c2+0.0))-((c1+0.0)*(a2+0.0)))/(dd + 0.0);
          p = new Point((int)xx,(int)yy);
          // el punto tiene que estar dentro de los dominios en 'X' y en 'Y'
          // en los dos segmentos
          if((enDom(xx,(u1+0.0),(u2+0.0)))&&(enDom(xx,(x1+0.0),(x2+0.0)))&&(enDom(yy,(v1+0.0),(v2+0.0)))&&(enDom(yy,(y1+0.0),(y2+0.0))))  // si esta en el dominio
            res = 1;
          else
            res = 4;
        }

      }
        return p;
    }  
    
    public static boolean estaEnSegmento(Point p, Point s1, Point s2, int r){
        boolean b = true;
        double a1,b1,c1,an,bn,cn,di;
        //ecuacion general de la recta...................................
        //    ax + by = c
        //...............................................................

        // coeficientes de la recta (u1,v1)--(u2,v2)
        //a1 = v1-v2;
        //b1 = u2-u1;
        //c1 = (u1*a1)+(v1*b1);
        
        double u1 = s1.getX();
        double v1 = s1.getY();
        double u2 = s2.getX();
        double v2 = s2.getY();
        a1 = v1-v2;
        b1 = u2-u1;
        c1 = (u1*a1)+(v1*b1);
        
        double rab = Math.sqrt((a1*a1)+(b1*b1));
        an = a1 / rab;
        bn = b1 / rab;
        cn = c1 / rab;
        
        di = Math.abs((an*p.getX())+(bn*p.getY())-(cn));
        if(di > r) b = false;
        if(b)
        {
            double a2,b2,c2,xx,yy,dd,x0,y0;
            a2 = b1;
            b2 = -a1;
            x0 = p.getX();
            y0 = p.getY();
            c2 = (b1*x0)-(a1*y0);
            
            dd = (a1*b2)-(b1*a2);
            
            // punto de interseccion (xx,yy)
            xx = (((c1+0.0)*(b2+0.0))-((b1+0.0)*(c2+0.0)))/(dd + 0.0);
            yy = (((a1+0.0)*(c2+0.0))-((c1+0.0)*(a2+0.0)))/(dd + 0.0);
            Point pp;
            pp = new Point((int)xx,(int)yy);
            if (! sonIguales((getDistancia(s1,pp)+getDistancia(pp,s2)),getDistancia(s1,s2))) 
                b = false;
        }
        
        return b;
    }
    
    public static Point[] getPuntosTriangulo(Point p1, Point p2) {
        Point[] p = new Point[2];
        double x = ((2*p1.getX())-(p2.getY()-p1.getY()))/2;
        double y = ((2*p1.getY())+(p2.getX()-p1.getX()))/2;
        p[0] = new Point((int)x,(int)y);
        double a = (2*p1.getX())-x;
        double b = (2*p1.getY())-y;
        p[1] = new Point((int)a,(int)b);
        return p;
    }
    
    public static Point interseccionRectanguloSegmento(Point rp1, Point rp2, Point rp3, Point rp4, Point sp1, Point sp2){
        Point p = new Point();
        if (tipoInterseccion((int)rp1.getX(),(int)rp1.getY(),(int)rp2.getX(),(int)rp2.getY(),(int)sp1.getX(),(int)sp1.getY(),(int)sp2.getX(),(int)sp2.getY())==1){
            return interseccionDeSegmentos((int)rp1.getX(),(int)rp1.getY(),(int)rp2.getX(),(int)rp2.getY(),(int)sp1.getX(),(int)sp1.getY(),(int)sp2.getX(),(int)sp2.getY());
        }
        if (tipoInterseccion((int)rp1.getX(),(int)rp1.getY(),(int)rp3.getX(),(int)rp3.getY(),(int)sp1.getX(),(int)sp1.getY(),(int)sp2.getX(),(int)sp2.getY())==1){
            return interseccionDeSegmentos((int)rp1.getX(),(int)rp1.getY(),(int)rp3.getX(),(int)rp3.getY(),(int)sp1.getX(),(int)sp1.getY(),(int)sp2.getX(),(int)sp2.getY());
        }
        if (tipoInterseccion((int)rp2.getX(),(int)rp2.getY(),(int)rp4.getX(),(int)rp4.getY(),(int)sp1.getX(),(int)sp1.getY(),(int)sp2.getX(),(int)sp2.getY())==1){
            return interseccionDeSegmentos((int)rp2.getX(),(int)rp2.getY(),(int)rp4.getX(),(int)rp4.getY(),(int)sp1.getX(),(int)sp1.getY(),(int)sp2.getX(),(int)sp2.getY());
        }
        if (tipoInterseccion((int)rp3.getX(),(int)rp3.getY(),(int)rp4.getX(),(int)rp4.getY(),(int)sp1.getX(),(int)sp1.getY(),(int)sp2.getX(),(int)sp2.getY())==1){
            return interseccionDeSegmentos((int)rp3.getX(),(int)rp3.getY(),(int)rp4.getX(),(int)rp4.getY(),(int)sp1.getX(),(int)sp1.getY(),(int)sp2.getX(),(int)sp2.getY());
        }
        return p;
        
    }
    
    public static boolean estaDentroRectangulo(Point pGrande, Dimension dGrande, Point pChico, Dimension dChico){
        double gx,gy,gx2,gy2;
        double cx,cy,cx2,cy2;
        gx = pGrande.getX();
        gy = pGrande.getY();
        gx2 = gx+dGrande.getWidth();
        gy2 = gy+dGrande.getHeight();
        cx = pChico.getX();
        cy = pChico.getY();
        cx2 = cx + dChico.getWidth();
        cy2 = cy + dChico.getHeight();
        
        /*System.out.println("////////////////////////////////////////////////////////");
        System.out.println(pGrande +" --> "+ dGrande);
        System.out.println(pChico + " --> " + dChico);
        System.out.println("gx="+gx + ", gy="+gy+"; gx2="+gx2+", gy2="+gy2);
        System.out.println("cx="+cx + ", cy="+cy+"; cx2="+cx2+", cy2="+cy2);
        System.out.println("////////////////////////////////////////////////////////");*/
        
        boolean b =((cx>=gx)&&(cx<=gx2)&&(cx2>=gx)&&(cx2<=gx2)&&
            (cy>=gy)&&(cy<=gy2)&&(cy2>=gy)&&(cy2<=gy2)); 
        System.out.println(b);
        return b;
        
        
    }
    

}
