public class Planet {
    private static final double G = 6.67 * 1e-11; //Gravitational constant

    public double xxPos; // current x position;
    public double yyPos; // current y position;
    public double xxVel; // current velocity in the x direction;
    public double yyVel; // current velocity in the y direction;
    public double mass; // mass;
    public String imgFileName; // name of the file that corresponds to the image that depicts the planet, e.g. jupiter.gif

    /**
     * Constructor to initialize an instance of the Planet class with given parameters
     * @param xP
     * @param yP
     * @param xV
     * @param yV
     * @param m
     * @param img
     */
    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /**
     * Constructor to copy given Planet object
     */
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
        /**
            Below can replace all above
            this.Planet(p.xxPos,p.yyPos,p.xxVel,p.yyVel,p.mass,p.imgFileName);
         **/
    }

    /**
        Calculate the distance between two Planets
     **/
    public double calcDistance(Planet p){
        double diffX = xxPos-p.xxPos;
        double diffY = yyPos-p.yyPos;
        double dist = Math.sqrt(diffX*diffX+diffY*diffY);
        return dist;
    }

    /**
     * Calculates the force exerted on this planet by the given planet
     */
    public double calcForceExertedBy(Planet p){
        double r = calcDistance(p);
        double F = G*mass*p.mass/(r*r);
        return F;
    }

    /**
     * Calculate the force exerted in X direction
     */
    public double calcForceExertedByX(Planet p){
        double F = calcForceExertedBy(p);
        double r = calcDistance(p);
        double diffX = p.xxPos-xxPos;
        double fx = F*diffX/r;
        return fx;
    }

    /**
     * Calculate the force exerted in Y direction
     */
    public double calcForceExertedByY(Planet p){
        double F = calcForceExertedBy(p);
        double r = calcDistance(p);
        double diffY = p.yyPos - yyPos;
        double fy = F*diffY/r;
        return fy;
    }
    /**
     Judges this.Planet is equal to given Plane
     */
    private Boolean equals(Planet p){
        if (xxPos==p.xxPos && yyPos==p.yyPos && xxVel==p.xxVel && yyVel==p.yyVel && mass==p.mass && imgFileName==p.imgFileName){
            return true;
        } else {
            return false;
        }
    }

    /**
     * calculate the net X force exerted by all planets in that array upon the current Planet
     */
    public double calcNetForceExertedByX(Planet[] allPlanets){
        double fxNet = 0.0;
        for (Planet p: allPlanets){
            if (equals(p)){
                continue;
            } else {
                fxNet = fxNet+calcForceExertedByX(p);
            }
        }
        return fxNet;
    }

    /**
     * calculate the net X force exerted by all planets in that array upon the current Planet
     */
    public double calcNetForceExertedByY(Planet[] allPlanets){
        double fyNet = 0.0;
        for (Planet p: allPlanets){
            if (equals(p)){
                continue;
            } else {
                fyNet = fyNet+calcForceExertedByY(p);
            }
        }
        return fyNet;
    }

    /**
     * update the planet’s position and velocity instance variables
     */
    public void update(double dt, double fX, double fY){
        double ax = fX/this.mass;
        double ay = fY/this.mass;

        this.xxVel = this.xxVel+dt*ax;
        this.yyVel = this.yyVel+dt*ay;
        this.xxPos = this.xxPos+dt*this.xxVel;
        this.yyPos = this.yyPos+dt*this.yyVel;
    }

    /**
     Draws this.Planet at its position
     */
    public void draw() {
        StdDraw.picture(xxPos, yyPos, "./images/" + imgFileName);
    }
}