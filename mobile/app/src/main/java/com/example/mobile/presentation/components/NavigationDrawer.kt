package com.example.mobile.presentation.components
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.example.mobile.R
import com.example.mobile.presentation.home.HomeView
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_White
import com.example.mobile.presentation.ui.theme.IPCA_Green_Light



// --- Menu Data Model ---
data class DrawerMenuItem(
    val id: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun NavigationDrawer(
    currentScreen: String,
    onItemSelected: (String) -> Unit,
    onLogout: () -> Unit
) {
    val menuItems = listOf(
        DrawerMenuItem("dashboard", "Visão Geral", Icons.Default.Dashboard), // Maps to AdminDashboardScreen
        DrawerMenuItem("requests", "Pedidos de Apoio", Icons.Default.Inbox), // Maps to AdminSupportListScreen
        DrawerMenuItem("stock", "Inventário", Icons.Default.Inventory),      // Maps to StockListScreen
        DrawerMenuItem("products", "Produtos", Icons.Default.ShoppingBag),   // Maps to ProductsScreen
        DrawerMenuItem("types", "Categorias", Icons.Default.Category),
        DrawerMenuItem("students", "Estudantes", Icons.Default.Person),
        DrawerMenuItem("studentsreq", "Meus Pedidos", Icons.Default.Person)
    )

    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        drawerContentColor = IPCA_Green_Dark,
        windowInsets = WindowInsets(0.dp)
    ) {
        // 1. Drawer Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(IPCA_Green_Dark)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 24.dp)
        ) {
            // User Avatar Placeholder
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User",
                    tint = IPCA_Green_Dark,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Pedro Duarte",
                color = Text_White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "a27990@alunos.ipca.pt",
                color = Text_White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 2. Menu Items
        menuItems.forEach { item ->
            NavigationDrawerItem(
                label = { Text(text = item.title, fontWeight = FontWeight.Medium) },
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                selected = currentScreen == item.id,
                onClick = { onItemSelected(item.id) },
                modifier = Modifier.padding(horizontal = 12.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = IPCA_Green_Light.copy(alpha = 0.15f),
                    selectedIconColor = IPCA_Green_Dark,
                    selectedTextColor = IPCA_Green_Dark,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                ),
                shape = MaterialTheme.shapes.small
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.weight(1f)) // Push logout to bottom

        // 3. Footer (Logout)
        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
        NavigationDrawerItem(
            label = { Text("Terminar Sessão") },
            icon = { Icon(Icons.Default.ExitToApp, null) },
            selected = false,
            onClick = onLogout,
            modifier = Modifier.padding(12.dp),
            colors = NavigationDrawerItemDefaults.colors(
                unselectedTextColor = Color.Red,
                unselectedIconColor = Color.Red
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerPreview(
) {
    NavigationDrawer("Beneficiários", {}, {})
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomePreview(
) {
    HomeView(navController = rememberNavController())
}