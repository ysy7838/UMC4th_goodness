package umc.precending.service.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.precending.domain.restaurant.Restaurant;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Service
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RestaurantService {
    @Autowired
    private DataSource dataSource;
    public LinkedList<Restaurant> setRestaurantList() {
        LinkedList<Restaurant> restaurantList = new LinkedList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement("SELECT DISTINCT * FROM rest ORDER BY RAND()");
            rs = pstmt.executeQuery();

            for (int i=0; i<10; i++) {
                Restaurant restaurant = new Restaurant();
                rs.next();
                restaurant.setRestaurantPoint(rs.getInt("num"), rs.getString("name"), rs.getString("adres"), rs.getString("category"), rs.getString("time"), rs.getString("lat"), rs.getString("lon"));
                restaurantList.add(restaurant);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return restaurantList;
    }

    public Restaurant getRestrauntInfo(String name) {
        Restaurant restaurant = new Restaurant();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(String.format("SELECT DISTINCT * FROM rest WHERE name = '%s'", name));
            rs = pstmt.executeQuery();
            rs.next();
            restaurant.setRestaurant(rs.getInt("num"), rs.getString("name"), rs.getString("adres"), rs.getString("category"), rs.getString("time"));

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurant;
    }

    public LinkedList<Restaurant> setRestaurantMapList() {
        LinkedList<Restaurant> restaurantList = new LinkedList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement("SELECT DISTINCT * FROM rest");
            rs = pstmt.executeQuery();

            while (!rs.isLast()) {
                Restaurant restaurant = new Restaurant();
                rs.next();
                restaurant.setRestaurantPoint(rs.getInt("num"), rs.getString("name"), rs.getString("adres"), rs.getString("category"), rs.getString("time"), rs.getString("lat"), rs.getString("lon"));
                restaurantList.add(restaurant);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurantList;
    }

}
