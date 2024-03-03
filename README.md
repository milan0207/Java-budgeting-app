# Java-Based Product Management Application

This project is a Java-based application that provides a graphical user interface for managing and viewing product data. The application utilizes Swing for the UI components and JFreeChart for data visualization.

## Application Panels

1. **MainPanel**: The main panel of the application, featuring buttons for adding a product, viewing a product, and accessing statistics.

2. **AddProductPanel**: This panel is designed for adding new products to the application. It features a back button for navigation, text fields for entering the product's name, price, and date, a combo box for selecting the product's category, and a button for adding the product.

3. **ViewProductPanel**: Displays a table of product data loaded from a text file. It includes a button to view detailed statistics related to the products.

4. **ViewStatisticsPanel**: Provides a detailed view of product statistics, allowing users to filter data by year and month. This panel displays a pie chart illustrating spending by category for the selected time period.


## Data Storage

The application uses a text file (`products.txt`) as a simple database to store product data. Each line in the file represents a product, with fields separated by a dollar sign (`$`). The fields include "Name," "Price," "Category," and "Date."

## Potential Enhancements

1. **Add Product Form**: Introduce a form for adding new products to streamline the product management process.

2. **Upgrade Data Storage**: Consider upgrading the current text file-based storage to a more robust SQL database for improved security and efficiency. This upgrade is already in the planning stages.

3. **Expanded Statistics**: Enhance the statistics feature to provide a more comprehensive overview of the product data.

4. **User Authentication System**: Consider implementing a user authentication system to ensure secure access to the application.

## Future Considerations

This project has the potential for further improvement, and the upcoming enhancements aim to make the application more user-friendly, secure, and feature-rich.
