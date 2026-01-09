package com.example.mobile.presentation.requests
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R // SUBSTITUA pelo seu pacote
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_Grey
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// --- Cores do Tema ---
val IPCA_Green_Dark = Color(0xFF004E36)
val IPCA_Gold = Color(0xFF8D764F)
val Background_Light = Color(0xFFF8F9FA)
val Text_Black = Color(0xFF333333)
val Warning_Orange = Color(0xFFE65100)
val Alert_Red = Color(0xFFB00020)

// --- NOVOS Modelos baseados no JSON ---

data class ApiRequestItem(
    val id: String,
    val productId: String,
    val productName: String,
    val qtyRequested: Int,
    val qtyDelivered: Int,
    val observation: String
)

data class ApiRequest(
    val id: String,
    val date: String, // ISO String: "2026-01-08T18:21:31.087Z"
    val status: String, // "PENDENTE", "CANCELADO", "ENTREGUE"
    val observation: String,
    val studentId: String,
    val items: List<ApiRequestItem>
)



@RequiresApi(Build.VERSION_CODES.O) // Necessário para formatação de data
@Composable
fun RequestsView(
    onMenuClick: () -> Unit,
    onTicketClick: (String) -> Unit
) {
    // --- Mock Data baseado no seu JSON ---
    val requests = listOf(
        ApiRequest(
            id = "83dc5ece-e885-4e66-8408-78e8018ad408",
            date = "2026-01-08T18:21:31.087Z",
            status = "PENDENTE",
            observation = "Need food for the week",
            studentId = "f5f101e6-6951-4270-8b0b-8d5a0c0913aa",
            items = listOf(
                ApiRequestItem("1", "p1", "Rice 1kg", 2, 0, "Rice"),
                ApiRequestItem("2", "p2", "Pasta 1kg", 2, 0, "Pasta")
            )
        ),
        ApiRequest(
            id = "e0d359e1-fefc-49fb-b593-9f7e26e5fad4",
            date = "2026-01-06T23:43:55.590Z",
            status = "CANCELADO",
            observation = "Need food for the week",
            studentId = "f5f101e6-6951-4270-8b0b-8d5a0c0913aa",
            items = listOf(
                ApiRequestItem("3", "p1", "Rice 1kg", 2, 0, "Rice")
            )
        )
    )

    // Mapa simulado de StudentID -> Nome (Na API real, você faria um fetch do user)
    val mockStudentNames = mapOf(
        "f5f101e6-6951-4270-8b0b-8d5a0c0913aa" to "Ana Silva (2021001)"
    )

    // --- State ---
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Pendentes, 1 = Histórico

    // Lógica de Filtro Atualizada
    val filteredList = requests.filter { request ->
        if (selectedTab == 0) {
            request.status == "PENDENTE"
        } else {
            request.status == "CANCELADO" || request.status == "ENTREGUE"
        }
    }

    Scaffold(
        containerColor = Background_Light
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            // 1. Header (Standard IPCA Admin Header)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(IPCA_Green_Dark)
                    .padding(top = 24.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo_ipca),
                        contentDescription = "Logo",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Pedidos de Apoio",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Bens Alimentares",
                            color = Color(0xFFA0C4B5), // Light Green text
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // 2. Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = IPCA_Green_Dark,
                contentColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = IPCA_Gold,
                        height = 3.dp
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Pendentes", fontWeight = if(selectedTab==0) FontWeight.Bold else FontWeight.Normal) },
                    selectedContentColor = IPCA_Gold,
                    unselectedContentColor = Text_Grey
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Histórico", fontWeight = if(selectedTab==1) FontWeight.Bold else FontWeight.Normal) },
                    selectedContentColor = IPCA_Gold,
                    unselectedContentColor = Text_Grey
                )
            }

            // 3. Lista
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (filteredList.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Nenhum pedido encontrado.", color = Color.Gray)
                        }
                    }
                } else {
                    items(filteredList) { request ->
                        val studentName = mockStudentNames[request.studentId] ?: "Estudante Desconhecido"
                        RequestCard(
                            request = request,
                            studentName = studentName,
                            onClick = { onTicketClick(request.id) }
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RequestCard(request: ApiRequest, studentName: String, onClick: () -> Unit) {

    // Formatador de data
    val formattedDate = remember(request.date) {
        try {
            val instant = Instant.parse(request.date)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault())
            formatter.format(instant)
        } catch (e: Exception) {
            request.date
        }
    }

    // Resumo dos itens (Ex: "2x Rice 1kg, 1x Pasta")
    val itemsSummary = remember(request.items) {
        if (request.items.isEmpty()) "Sem itens especificados"
        else request.items.joinToString(", ") { "${it.qtyRequested}x ${it.productName}" }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Linha 1: Status e Data
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusBadge(status = request.status)
                Text(text = formattedDate, fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Linha 2: Observação (Motivo)
            Text(
                text = request.observation,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Text_Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Linha 3: Resumo dos Itens (Novo)
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.ShoppingBasket, null, tint = IPCA_Green_Dark, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = itemsSummary,
                    fontSize = 13.sp,
                    color = Text_Black,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(8.dp))

            // Linha 4: Nome do Estudante e Seta
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = studentName,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = IPCA_Gold,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val (color, text, icon) = when (status) {
        "PENDENTE" -> Triple(Warning_Orange, "Pendente", Icons.Default.WarningAmber)
        "ENTREGUE" -> Triple(IPCA_Green_Dark, "Entregue", Icons.Default.CheckCircle)
        "CANCELADO" -> Triple(Alert_Red, "Cancelado", Icons.Default.Block)
        else -> Triple(Color.Gray, status, Icons.Default.History)
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = color, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun AdminListPreview() {
    RequestsView(onMenuClick = {}, onTicketClick = {})
}