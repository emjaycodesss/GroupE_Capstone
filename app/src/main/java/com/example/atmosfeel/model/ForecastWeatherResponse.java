package com.example.atmosfeel.model;

import java.io.Serializable;
import java.util.List;

public class ForecastWeatherResponse implements Serializable {
    private String cod;
    private int message;
    private int cnt;
    private List<WeatherItem> list;
    private City city;


    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<WeatherItem> getList() {
        return list;
    }

    public void setList(List<WeatherItem> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public static class WeatherItem implements Serializable {
        private long dt;
        private Main main;
        private List<Weather> weather;
        private Clouds clouds;
        private Wind wind;
        private int visibility;
        private double pop;
        private Sys sys;
        private String dt_txt;

        public WeatherItem(long dt, Main main, List<Weather> weather, Clouds clouds, Wind wind, int visibility, double pop, Sys sys, String dt_txt) {
            this.dt = dt;
            this.main = main;
            this.weather = weather;
            this.clouds = clouds;
            this.wind = wind;
            this.visibility = visibility;
            this.pop = pop;
            this.sys = sys;
            this.dt_txt = dt_txt;
        }

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public int getVisibility() {
            return visibility;
        }

        public void setVisibility(int visibility) {
            this.visibility = visibility;
        }

        public double getPop() {
            return pop;
        }

        public void setPop(double pop) {
            this.pop = pop;
        }

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }

        public String getDt_txt() {
            return dt_txt;
        }

        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
        }

        public static class Main implements Serializable {
            private double temp;
            private double feels_like;
            private double temp_min;
            private double temp_max;
            private int pressure;
            private int sea_level;
            private int grnd_level;
            private int humidity;
            private double temp_kf;

            public Main(double temp, double feels_like, double temp_min, double temp_max, int pressure, int sea_level, int grnd_level, int humidity, double temp_kf) {
                this.temp = temp;
                this.feels_like = feels_like;
                this.temp_min = temp_min;
                this.temp_max = temp_max;
                this.pressure = pressure;
                this.sea_level = sea_level;
                this.grnd_level = grnd_level;
                this.humidity = humidity;
                this.temp_kf = temp_kf;
            }

            public double getTemp() {
                return temp;
            }

            public void setTemp(double temp) {
                this.temp = temp;
            }

            public double getFeels_like() {
                return feels_like;
            }

            public void setFeels_like(double feels_like) {
                this.feels_like = feels_like;
            }

            public double getTemp_min() {
                return temp_min;
            }

            public void setTemp_min(double temp_min) {
                this.temp_min = temp_min;
            }

            public double getTemp_max() {
                return temp_max;
            }

            public void setTemp_max(double temp_max) {
                this.temp_max = temp_max;
            }

            public int getPressure() {
                return pressure;
            }

            public void setPressure(int pressure) {
                this.pressure = pressure;
            }

            public int getSea_level() {
                return sea_level;
            }

            public void setSea_level(int sea_level) {
                this.sea_level = sea_level;
            }

            public int getGrnd_level() {
                return grnd_level;
            }

            public void setGrnd_level(int grnd_level) {
                this.grnd_level = grnd_level;
            }

            public int getHumidity() {
                return humidity;
            }

            public void setHumidity(int humidity) {
                this.humidity = humidity;
            }

            public double getTemp_kf() {
                return temp_kf;
            }

            public void setTemp_kf(double temp_kf) {
                this.temp_kf = temp_kf;
            }
        }

        public static class Weather implements Serializable {
            private int id;
            private String main;
            private String description;
            private String icon;

            public Weather(int id, String main, String description, String icon) {
                this.id = id;
                this.main = main;
                this.description = description;
                this.icon = icon;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }

        public static class Clouds implements Serializable {
            private int all;

            public Clouds(int all) {
                this.all = all;
            }

            public int getAll() {
                return all;
            }

            public void setAll(int all) {
                this.all = all;
            }
        }

        public static class Wind implements Serializable {
            private double speed;
            private int deg;
            private double gust;

            public Wind(double speed, int deg, double gust) {
                this.speed = speed;
                this.deg = deg;
                this.gust = gust;
            }

            public double getSpeed() {
                return speed;
            }

            public void setSpeed(double speed) {
                this.speed = speed;
            }

            public int getDeg() {
                return deg;
            }

            public void setDeg(int deg) {
                this.deg = deg;
            }

            public double getGust() {
                return gust;
            }

            public void setGust(double gust) {
                this.gust = gust;
            }
        }

        public static class Sys implements Serializable {
            private String pod;

            public Sys(String pod) {
                this.pod = pod;
            }

            public String getPod() {
                return pod;
            }

            public void setPod(String pod) {
                this.pod = pod;
            }
        }
    }

    public static class City implements Serializable {
        private int id;
        private String name;
        private Coord coord;
        private String country;
        private int population;
        private int timezone;
        private long sunrise;
        private long sunset;

        public City(int id, String name, Coord coord, String country, int population, int timezone, long sunrise, long sunset) {
            this.id = id;
            this.name = name;
            this.coord = coord;
            this.country = country;
            this.population = population;
            this.timezone = timezone;
            this.sunrise = sunrise;
            this.sunset = sunset;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Coord getCoord() {
            return coord;
        }

        public void setCoord(Coord coord) {
            this.coord = coord;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }

        public int getTimezone() {
            return timezone;
        }

        public void setTimezone(int timezone) {
            this.timezone = timezone;
        }

        public long getSunrise() {
            return sunrise;
        }

        public void setSunrise(long sunrise) {
            this.sunrise = sunrise;
        }

        public long getSunset() {
            return sunset;
        }

        public void setSunset(long sunset) {
            this.sunset = sunset;
        }

        public static class Coord implements Serializable {
            private double lat;
            private double lon;

            public Coord(double lat, double lon) {
                this.lat = lat;
                this.lon = lon;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }
        }
    }
}

