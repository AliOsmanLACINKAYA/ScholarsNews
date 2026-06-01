package com.example.scholarsnews.ui

import android.R.attr.onClick
import android.content.Intent
import android.net.Uri
import android.net.http.SslCertificate.saveState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scholarsnews.models.News
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.scholarsnews.models.Scholarship
import com.example.scholarsnews.R
import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.platform.LocalConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var showLoginSheet by remember { mutableStateOf(false) }
    var showRegisterSheet by remember { mutableStateOf(false) }

    // Giriş verileri
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Uygulama Logosu",
            modifier = Modifier
                .size(230.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Fit
        )
        Text("ScholarNews AI", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(48.dp))

        Button(onClick = { showLoginSheet = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Giriş Yap")
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = { showRegisterSheet = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Kayıt Ol")
        }
    }

    if (showLoginSheet) {
        ModalBottomSheet(onDismissRequest = { showLoginSheet = false }) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Giriş Yap", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-posta") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Şifre") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(vertical = 8.dp))
                }

                Button(
                    onClick = {
                        if (email == "aliosman@gmail.com" && password == "ali1234") {
                            onLoginSuccess()
                        } else {
                            errorMessage = "Hatalı e-posta veya şifre!"
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Text("Giriş Yap")
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    if (showRegisterSheet) {
        ModalBottomSheet(onDismissRequest = { showRegisterSheet = false }) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Kayıt Ol", style = MaterialTheme.typography.headlineSmall)
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Google ile Devam Et") }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val allNews = listOf(
        News(title = "Yurt Dışı Staj Bursu", summary = "Avrupa'da staj yapmak isteyenler için...", url = "https://www.google.com/haber1"),
        News(title = "Kodlama Maratonu", summary = "Bu hafta sonu düzenlenecek yarışma...", url = "https://www.ankara.edu.tr"),
        News(title = "AI ve Eğitim Zirvesi", summary = "Yapay zekanın eğitimdeki rolü tartışılacak...", url = "https://www.ankara.edu.tr"),
        News(title = "Kütüphane Haftası", summary = "Özel etkinlikler ve söyleşiler başlıyor...", url = "https://www.ankara.edu.tr")
    )

    val allScholarships = listOf(
        Scholarship(title = "Başarı Bursu", institution = "Ankara Üni.", amount = "5000 TL", url = "https://www.ankara.edu.tr"),
        Scholarship(title = "Teknoloji Desteği", institution = "TÜBİTAK", amount = "8000 TL", url = "https://www.ankara.edu.tr"),
        Scholarship(title = "Sporcu Bursu", institution = "Gençlik ve Spor Bak.", amount = "4000 TL", url = "https://www.ankara.edu.tr"),
        Scholarship(title = "Yemek Desteği", institution = "Ankara Üni.", amount = "2000 TL", url = "https://www.ankara.edu.tr")
    )

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Categories,
        BottomNavItem.Favorites,
        BottomNavItem.Profile
    )

    val navController = rememberNavController()
    val favoriteNews = remember { mutableStateListOf<News>() }
    val favoriteScholarships = remember { mutableStateListOf<Scholarship>() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val configuration = LocalConfiguration.current
    val isLandscapeTablet = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && configuration.screenWidthDp > 600
    val isLogin = currentRoute == "login"

    // Ana sayfalar (Menü görünmeli, Geri butonu görünmemeli)
    val isTopLevel = currentRoute in listOf("home", "categories", "favorites", "profile")

    // Alt menü: Login hariç her yerde görünecek
    val showBottomBar = !isLogin

    // Geri butonu: Login değilse VE ana sayfa değilse görünsün
    val showBackButton = !isLogin && !isTopLevel
    Row(modifier = Modifier.fillMaxSize()) {

        if (isLandscapeTablet && showBottomBar) {
            NavigationRail(modifier = Modifier.fillMaxHeight()) {
                items.forEach { item ->
                    NavigationRailItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                        label = { Text(text = item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo("home") { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }


        Scaffold(
            modifier = Modifier.weight(1f),
            topBar = {
                if (showBackButton) {
                    TopAppBar(
                        title = { Text("Geri") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri Dön")
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (!isLandscapeTablet && showBottomBar) {
                    NavigationBar {
                        items.forEach { item ->
                            NavigationBarItem(
                                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                                label = { Text(text = item.title) },
                                selected = currentRoute == item.route,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo("home") { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            // NavHost içeriği
            NavHost(
                navController = navController,
                startDestination = "login",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("login") { LoginScreen(onLoginSuccess = { navController.navigate("home") { popUpTo("login") { inclusive = true } } }) }
                composable("home") {
                    HomeScreen(
                        onNewsClick = { news -> navController.navigate("news_detail/${news.title}") },
                        onScholarshipClick = { scholar -> navController.navigate("scholarship_detail/${scholar.title}") },
                        showOnlyNews = false, favoriteNews = favoriteNews, favoriteScholarships = favoriteScholarships,
                        onToggleFavorite = { news -> if (!favoriteNews.contains(news)) favoriteNews.add(news) else favoriteNews.remove(news) },
                        onToggleFavoriteScholarship = { scholar -> if (!favoriteScholarships.contains(scholar)) favoriteScholarships.add(scholar) else favoriteScholarships.remove(scholar) }
                    )
                }
                composable("news_only") {
                    HomeScreen(
                        onNewsClick = { news -> navController.navigate("news_detail/${news.title}") },
                        onScholarshipClick = { }, showOnlyNews = true, favoriteNews = favoriteNews, favoriteScholarships = favoriteScholarships,
                        onToggleFavorite = { news -> if (!favoriteNews.contains(news)) favoriteNews.add(news) else favoriteNews.remove(news) },
                        onToggleFavoriteScholarship = { scholar -> if (!favoriteScholarships.contains(scholar)) favoriteScholarships.add(scholar) else favoriteScholarships.remove(scholar) }
                    )
                }
                composable("news_detail/{newsTitle}") { backStackEntry ->
                    val title = backStackEntry.arguments?.getString("newsTitle") ?: ""
                    NewsDetailScreen(
                        title = title,
                        allNews = allNews,
                        favoriteNews = favoriteNews,
                        onToggleNews = { news ->
                            if(favoriteNews.contains(news)) favoriteNews.remove(news)
                            else favoriteNews.add(news)
                        },
                        // --- DÜZELTME BURADA ---
                        onNewsClick = { news -> navController.navigate("news_detail/${news.title}") }
                    )
                }
                composable("scholarships") {
                    ScholarshipScreen(
                        scholarships = allScholarships, // Buraya ana listeyi gönderiyoruz!
                        onScholarshipClick = { s -> navController.navigate("scholarship_detail/${s.title}") },
                        onToggleFavorite = { s ->
                            if(!favoriteScholarships.contains(s)) favoriteScholarships.add(s)
                            else favoriteScholarships.remove(s)
                        },
                        favoriteScholarships = favoriteScholarships
                    )
                }
                composable("scholarship_detail/{scholarTitle}") { backStackEntry ->
                    val title = backStackEntry.arguments?.getString("scholarTitle") ?: ""
                    ScholarshipDetailScreen(
                        title = title,
                        allScholarships = allScholarships,
                        favoriteScholarships = favoriteScholarships,
                        onToggleScholarship = { s ->
                            if(favoriteScholarships.contains(s)) favoriteScholarships.remove(s)
                            else favoriteScholarships.add(s)
                        },
                        // --- DÜZELTME BURADA ---
                        onScholarshipClick = { scholar -> navController.navigate("scholarship_detail/${scholar.title}") }
                    )
                }
                composable("categories") {
                    CategoriesScreen(onCategoryClick = { categoryName ->
                        // İŞTE EKSİK OLAN KISIM BURASI:
                        when (categoryName) {
                            "Haberler" -> navController.navigate("news_only")
                            "Burslar" -> navController.navigate("scholarships")
                            else -> navController.navigate("coming_soon/$categoryName")
                        }
                    })
                }
                composable("coming_soon/{categoryName}") { backStackEntry ->
                    val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                    // Eğer kategori "Avantajlı Fırsatlar" ise onu göster, değilse ComingSoon
                    if (categoryName == "Avantajlı Fırsatlar (Çok Yakında)") {
                        AdvantageScreen(categoryName)
                    } else {
                        ComingSoonScreen(categoryName)
                    }
                }
                composable("coming_soon/{categoryName}") { AdvantageScreen(it.arguments?.getString("categoryName") ?: "") }
                composable("favorites") {
                    FavoritesScreen(
                        newsList = favoriteNews,
                        scholarshipList = favoriteScholarships,
                        onNewsClick = { news -> navController.navigate("news_detail/${news.title}") },
                        onScholarshipClick = { scholar -> navController.navigate("scholarship_detail/${scholar.title}") },
                        onToggleNews = { news -> favoriteNews.remove(news) },
                        onToggleScholarship = { scholar -> favoriteScholarships.remove(scholar) },

                        // --- DÜZELTME BURADA ---
                        onClearAll = {
                            favoriteNews.clear()
                            favoriteScholarships.clear()
                        },
                        // -----------------------

                        onNavigateHome = { navController.navigate("home") { popUpTo("favorites") { inclusive = true } } }
                    )
                }
                composable("profile") { ProfileScreen(onLogoutClick = { navController.navigate("login") { popUpTo("home") { inclusive = true } } }) }
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(onNewsClick: (News) -> Unit,
                onScholarshipClick: (Scholarship) -> Unit,
               showOnlyNews: Boolean = false,
               favoriteNews: List<News>,
               favoriteScholarships: List<Scholarship>,
                onToggleFavorite: (News) -> Unit,
                onToggleFavoriteScholarship: (Scholarship) -> Unit)
{
    val dummyCarouselImages = listOf(
        "Avrupa'da Staj!",
        "Kodlama Maratonu!",
        "Yapay Zeka Zirvesi!",
        "Burs Haberleri!"
    )
    val dummyNews = listOf(
        News(title = "Yurt Dışı Staj Bursu", summary = "Avrupa'da staj yapmak isteyenler için...", url = "https://www.google.com/haber1"),
        News(title = "Kodlama Maratonu", summary = "Bu hafta sonu düzenlenecek yarışma...", url = "https://www.ankara.edu.tr"),
        News(title = "AI ve Eğitim Zirvesi", summary = "Yapay zekanın eğitimdeki rolü tartışılacak...", url = "https://www.ankara.edu.tr"),
        News(title = "Kütüphane Haftası", summary = "Özel etkinlikler ve söyleşiler başlıyor...", url = "https://www.ankara.edu.tr")
    )

    val dummyScholarships = listOf(
        Scholarship(title = "Başarı Bursu", institution = "Ankara Üni.", amount = "5000 TL", url = "https://www.ankara.edu.tr"),
        Scholarship(title = "Teknoloji Desteği", institution = "TÜBİTAK", amount = "8000 TL", url = "https://www.ankara.edu.tr"),
        Scholarship(title = "Sporcu Bursu", institution = "Gençlik ve Spor Bak.", amount = "4000 TL", url = "https://www.ankara.edu.tr"),
        Scholarship(title = "Yemek Desteği", institution = "Ankara Üni.", amount = "2000 TL", url = "https://www.ankara.edu.tr")
    )
    val pagerState = rememberPagerState(initialPage = 0) { dummyCarouselImages.size }
    val titleText = if (showOnlyNews) "Güncel Haberler" else "ScholarNews AI - Manşet"

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        // Manşet ve Ana Başlık Alanı
        item {
            Text(text = titleText, style = MaterialTheme.typography.headlineSmall)

            if (!showOnlyNews) {
                val libraryNews = dummyNews.find { it.title == "Kütüphane Haftası" }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(vertical = 16.dp)
                        .clickable {
                            libraryNews?.let { onNewsClick(it) }
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.manset1),
                        contentDescription = "Manşet Görseli",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
                Text("Güncel İçerikler", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(vertical = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = MaterialTheme.shapes.medium
                )
                {
                    Box(modifier = Modifier.fillMaxSize()) {
                        HorizontalPager(
                            state = pagerState,
                            key = { dummyCarouselImages[it] },
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        if (page < dummyNews.size) {
                                            onNewsClick(dummyNews[page])
                                        }
                                    }
                                    .background(
                                        when (page) {
                                            0 -> MaterialTheme.colorScheme.primaryContainer
                                            1 -> MaterialTheme.colorScheme.secondaryContainer
                                            else -> MaterialTheme.colorScheme.tertiaryContainer
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dummyCarouselImages[page],
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .align(Alignment.BottomCenter)
                                .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f)),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(dummyCarouselImages.size) { iteration ->
                                val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.LightGray
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clip(MaterialTheme.shapes.extraSmall)
                                        .background(color)
                                        .size(8.dp)

                                )
                            }
                        }
                    }
                }
        }

        if (!showOnlyNews) {
            item {
                Text("Son Haberler", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 8.dp))
            }
        }

        items(dummyNews) { newsItem ->
            NewsCard(
                news = newsItem,
                isFavorite = favoriteNews.contains(newsItem),
                onClick = { onNewsClick(newsItem) },
                onToggleFavorite = { onToggleFavorite(newsItem) }
            )
        }

        if (!showOnlyNews) {
            item {
                Text("Güncel Burslar", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 8.dp))
            }
            items(dummyScholarships) { scholar ->
                ScholarshipCard(
                    scholarship = scholar,
                    isFavorite = favoriteScholarships.contains(scholar),
                    onClick = { onScholarshipClick(scholar) },
                    onToggleFavorite = { onToggleFavoriteScholarship(scholar) }
                )
            }
        }
    }
}

@Composable
fun NewsCard(news: News, isFavorite: Boolean, onClick: () -> Unit, onToggleFavorite: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = news.title, style = MaterialTheme.typography.titleMedium)
                Text(text = news.summary, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favori",
                    tint = if (isFavorite) androidx.compose.ui.graphics.Color.Red else androidx.compose.ui.graphics.Color.Gray
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    title: String,
    allNews: List<News>,
    favoriteNews: List<News>, // Favori listesi
    onToggleNews: (News) -> Unit, // Favori değiştirme
    onNewsClick: (News) -> Unit
) {
    val similarNews = allNews.filter { it.title != title }.take(2)
    val context = LocalContext.current
    val currentNews = allNews.find { it.title == title }
    var showPremiumSheet by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            val imageRes = when (title) {
                "Yurt Dışı Staj Bursu" -> R.drawable.manset2
                "Kütüphane Haftası" -> R.drawable.manset1
                else -> null // Diğerleri için varsayılan durum
            }
            Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                if (imageRes != null) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Haber Görseli",
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Görseli olmayanlar için varsayılan kutu
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        Text("Haber Görseli")
                    }
                }
            }
            Text(text = title, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Yapay zeka tarafından özetlenmiş haber içeriğinin tamamı burada yer alacaktır...", modifier = Modifier.padding(vertical = 16.dp))
            Button(
                onClick = { showPremiumSheet = true },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            ) {
                Text("İçeriğe Git (Premium Gerektirir)")
            }
            Text("Benzer Haberler:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
        }

        items(similarNews) { news ->
            NewsCard(
                news = news,
                isFavorite = favoriteNews.contains(news), // Listede var mı
                onClick = { onNewsClick(news) },
                onToggleFavorite = { onToggleNews(news) } // tetikleyici
            )
        }
    }
    if (showPremiumSheet) {
        ModalBottomSheet(onDismissRequest = { showPremiumSheet = false }) {
            Column(modifier = Modifier.padding(24.dp).padding(bottom = 48.dp)) {
                Text("Premium Ayrıcalıkları", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(24.dp))
                Text("🌟 Mobil bildirimler alın.", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("🌟 İçeriklere direkt erişin.", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                    Text("Premium Alın")
                }
            }
        }
    }
}

@Composable
fun ScholarshipCard(scholarship: Scholarship, isFavorite: Boolean, onClick: () -> Unit, onToggleFavorite: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = scholarship.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "Kurum: ${scholarship.institution}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Miktar: ${scholarship.amount}", style = MaterialTheme.typography.labelLarge)
            }
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favori",
                    tint = if (isFavorite) androidx.compose.ui.graphics.Color.Red else androidx.compose.ui.graphics.Color.Gray
                )
            }
        }
    }
}

@Composable
fun ScholarshipScreen(
    scholarships: List<Scholarship>, // Artık dışarıdan liste alacak
    onScholarshipClick: (Scholarship) -> Unit,
    onToggleFavorite: (Scholarship) -> Unit,
    favoriteScholarships: List<Scholarship>
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Güncel Burslar", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp))
        LazyColumn {
            // Dışarıdan gelen listeyi kullanıyoruz
            items(scholarships) { scholar ->
                ScholarshipCard(
                    scholarship = scholar,
                    isFavorite = favoriteScholarships.contains(scholar),
                    onClick = { onScholarshipClick(scholar) },
                    onToggleFavorite = { onToggleFavorite(scholar) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScholarshipDetailScreen(
    title: String,
    allScholarships: List<Scholarship>,
    favoriteScholarships: List<Scholarship>,
    onToggleScholarship: (Scholarship) -> Unit,
    onScholarshipClick: (Scholarship) -> Unit
) {

    val similarScholarships = allScholarships.filter { it.title != title }.take(2)
    val context = LocalContext.current
    val currentScholarship = allScholarships.find { it.title == title }
    var showPremiumSheet by remember { mutableStateOf(false) }
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            val imageRes = if (title == "Başarı Bursu") R.drawable.manset3 else null

            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column {
                    // Eğer görsel varsa göster
                    if (imageRes != null) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Burs Görseli",
                            modifier = Modifier.fillMaxWidth().height(150.dp),
                            contentScale = ContentScale.Crop
                        )

                        // DÜZELTME: Spacer'ı buraya, görselin hemen altına ekliyoruz
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Metinlerin olduğu iç Column
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = title, style = MaterialTheme.typography.headlineSmall)

                        // İstersen başlık ile kurum bilgisi arasına da biraz boşluk bırakabilirsin
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(text = "Kurum: ${currentScholarship?.institution ?: "Bilinmiyor"}", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Miktar: ${currentScholarship?.amount ?: "Belirtilmemiş"}", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            Text(
                text = "Bu burs programı, öğrencilerin akademik gelişimlerini desteklemek, eğitim masraflarına katkıda bulunmak ve proje bazlı çalışmalarını finanse etmek amacıyla oluşturulmuştur. Başvuru koşulları ve detaylar için ilgili kurumun resmi kanallarını takip ediniz.",
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            Button(
                onClick = { showPremiumSheet = true },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            ) {
                Text("İçeriğe Git (Premium Gerektirir)")
            }
            Text("Benzer Burslar:", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 8.dp))
        }

        items(similarScholarships) { scholar ->
            ScholarshipCard(
                scholarship = scholar,
                isFavorite = favoriteScholarships.contains(scholar),
                onClick = { onScholarshipClick(scholar) },
                onToggleFavorite = { onToggleScholarship(scholar) }
            )
        }
    }
    if (showPremiumSheet) {
        ModalBottomSheet(onDismissRequest = { showPremiumSheet = false }) {
            Column(modifier = Modifier.padding(24.dp).padding(bottom = 48.dp)) {
                Text("Premium Ayrıcalıkları", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(24.dp))
                Text("🌟 Mobil bildirimler alın.", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("🌟 İçeriklere direkt erişin.", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                    Text("Premium Alın")
                }
            }
        }
    }
}

@Composable
fun CategoriesScreen(onCategoryClick: (String) -> Unit) {
    val categories = listOf("Haberler", "Burslar", "Etkinlikler(Çok Yakında)", "Staj İlanları(Çok Yakında)", "Avantajlı Fırsatlar (Çok Yakında)")

    // DÜZELTME: Modifier.fillMaxSize() eklendi!
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Kategoriler", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp))

        // LazyColumn artık ekranın kalanını dolduracak
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(categories) { category ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onCategoryClick(category) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                        Text(text = category, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun ComingSoonScreen(categoryName: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Bu özellik çok yakında hizmetinizde olacak.",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun AdvantageScreen(categoryName: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "İndirimli etkinlikler, avantajlı burslar gibi fırsatlar çok yakında hizmetinizde olacak.",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun FavoritesScreen(
    newsList: List<News>,
    scholarshipList: List<Scholarship>,
    onNewsClick: (News) -> Unit,
    onScholarshipClick: (Scholarship) -> Unit,
    onToggleNews: (News) -> Unit,
    onToggleScholarship: (Scholarship) -> Unit,
    onClearAll: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val isNewsEmpty = newsList.isEmpty()
    val isScholarEmpty = scholarshipList.isEmpty()
    val isEntirelyEmpty = isNewsEmpty && isScholarEmpty

    if (isEntirelyEmpty) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Favoriler ekranı şu anda boş.", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onNavigateHome) {
                Text("Favori Ekle")
            }
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                Text("Favorilerim", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp))
            }

            if (!isNewsEmpty) {
                item {
                    Text("Favori Haberler", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 8.dp))
                }
                items(newsList) { news ->
                    NewsCard(
                        news = news,
                        isFavorite = true,
                        onClick = { onNewsClick(news) },
                        onToggleFavorite = { onToggleNews(news) }
                    )
                }
            }

            if (!isScholarEmpty) {
                item {
                    Text("Favori Burslar", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 8.dp))
                }
                items(scholarshipList) { scholar ->
                    ScholarshipCard(
                        scholarship = scholar,
                        isFavorite = true,
                        onClick = { onScholarshipClick(scholar) },
                        onToggleFavorite = { onToggleScholarship(scholar) }
                    )
                }
            }

            item {
                Button(
                    onClick = onClearAll,
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Favorileri Temizle")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onLogoutClick: () -> Unit) {
    var showPremiumSheet by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text("Profilim", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 24.dp))

        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ali Osman LAÇİNKAYA", style = MaterialTheme.typography.titleLarge)
                Text("Ankara Üniversitesi", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Text("Ayarlar", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Bildirimleri Yönet")
                Text("Koyu Mod (Çok Yakında)")
                Text(
                    text = "Çıkış Yap",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable { onLogoutClick() }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { showPremiumSheet = true },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = androidx.compose.ui.graphics.Color(0xFFFFD700), // Altın rengi
                contentColor = androidx.compose.ui.graphics.Color.Black
            )
        ) {
            Text("👑 Premium'a Yükselt")
        }
    }

    if (showPremiumSheet) {
        ModalBottomSheet(onDismissRequest = { showPremiumSheet = false }) {
            Column(modifier = Modifier.padding(24.dp).padding(bottom = 48.dp)) {
                Text("Premium Ayrıcalıkları", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(24.dp))

                Text("🌟 Mobil bildirimler alın.", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("🌟 İçeriklere direkt erişin.", style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { /* Boş */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Premium Alın")
                }
            }
        }
    }
}
