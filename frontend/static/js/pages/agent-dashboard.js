document.addEventListener('DOMContentLoaded', () => {
  const searchInput = document.getElementById('dashboard-search');
  const filterBtns = document.querySelectorAll('.filter-btn');
  const grid = document.getElementById('listings-grid');
  const prevBtn = document.getElementById('prev-btn');
  const nextBtn = document.getElementById('next-btn');
  const pageLinks = document.querySelectorAll('.page-link');

  let currentStatus = 'all';
  let currentPage = 1;
  const totalPages = 8;

  /* USER NAME */
  function updateDisplayName() {
    const savedName = localStorage.getItem('em_user_name');
    const nameElement = document.getElementById('user-name-display');
    if (savedName && nameElement) nameElement.textContent = savedName;
  }
  updateDisplayName();

  /* SEED DATA */
  function seedInitialProperties() {
    if (!localStorage.getItem('em_properties')) {
      localStorage.setItem('em_properties', JSON.stringify([
        { id:"static_1", title:"123 Maple Avenue", price:"540000", status:"active", address:"123 Maple Avenue", city:"Springfield", state:"IL", zip:"62704", sqft:"2100", beds:"3", baths:"2", image:"https://lh3.googleusercontent.com/aida-public/AB6AXuAwM2-7dBsX8TQzfDg3bEAlzyA5BBam3Rdc-FlYb2wUWG4P5YJQ54bMHhhxRkQfQ332AXHVfBYq46HL4pFuyvGTb-kGRkrwOwhGBsc5OYEfkghtklMg1RpsY9OTdcfSeD7J1SEnaslIu6IU9tzgHyRwq4LTWR8y7trHwuL1GcuOvtBJNrPqtVIoUfSTk4YBpbWeDXzahLl6sgVpXn5LdseKGJVTnMalhyeODZHJ0kIVGTn-ti8PYPNUYVfk9V38vU5UHeNG2VLMmw" },
        { id:"static_2", title:"782 Willow Lane", price:"325000", status:"active", address:"782 Willow Lane", city:"Austin", state:"TX", zip:"78701", sqft:"1050", beds:"2", baths:"1.5", image:"https://lh3.googleusercontent.com/aida-public/AB6AXuCAWBHzzSEYdIJGy-IRHLb1wrwjZzt1iKcNgG9fwy1NE76MNzt8-ZmsIWNgRJqRBa4BXIhnwUjUgnyBjXyD3qw2IM-P7UzxMBvl724IgxcwOgrR-xk0TtU7kVLlNXLQtl5Uzxfve9fhLdgFDSsUmgvR19d_IS_wMnZvFIH2Oea0eujy9S5kryGrKSy5FGpcKyBsDOzXu_YP9E4WK_xPD0f5LUXSPeaD0yZ0pDUj90vl2EeN1L5Chmv6cEKhUehR0Iowzp9SjmXE4w" },
        { id:"static_3", title:"900 Sunset Blvd", price:"1200000", status:"sold", address:"900 Sunset Blvd", city:"Los Angeles", state:"CA", zip:"90026", sqft:"3400", beds:"4", baths:"3.5", image:"https://lh3.googleusercontent.com/aida-public/AB6AXuARrkjUr_mzvR6O9OZsjNtRzSITvRdIR3fXLvSoklUdkHkOcEeiqzt9_jFL74SnrqG-4EArxXlo5a4WtAkNfUuKsp2QfuxVZaMeJmxe07cXc2xPOn8xA3Cbl4BATWHXqodro5aQcGZuB-dnPA00NxITNTKZ1eTPzvkDIw2sjN2i631d_fON6ACklyreSxvktxrCOg7Ai5-ChG8IIAENzlCvo_HyLzrSJj4xM9P69fUfbWFWzY9KqZI1ylas82WQHFLcIlRyEnTepA" }
      ]));
    }
  }
  seedInitialProperties();

  /* FILTER + PAGINATION (unchanged logic) */
  function applyFilters() {
    const query = searchInput.value.toLowerCase().trim();
    document.querySelectorAll('.property-card').forEach(card => {
      if (currentPage !== 1) return card.style.display = 'none';
      const matchesStatus = currentStatus === 'all' || card.dataset.status === currentStatus;
      const matchesSearch = card.innerText.toLowerCase().includes(query);
      card.style.display = matchesStatus && matchesSearch ? 'flex' : 'none';
    });
  }

  searchInput.addEventListener('input', () => { currentPage = 1; applyFilters(); });
  prevBtn.onclick = () => currentPage > 1 && (--currentPage, applyFilters());
  nextBtn.onclick = () => currentPage < totalPages && (++currentPage, applyFilters());
});
